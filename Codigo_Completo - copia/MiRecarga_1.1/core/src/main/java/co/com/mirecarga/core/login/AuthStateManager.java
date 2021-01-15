/*
 * Copyright 2017 The AppAuth for Android Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.com.mirecarga.core.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.AnyThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.RegistrationResponse;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.com.mirecarga.core.util.AppLog;

/**
 * An example persistence mechanism for an {@link AuthState} instance.
 * This stores the instance in a shared preferences file, and provides thread-safe access and
 * mutation.
 */
@Singleton
public final class AuthStateManager {
    /**
     * El tag para el log.
     */
    private static final String TAG = "AuthStateManager";

    /**
     * La clave de almacenamiento.
     */
    private static final String KEY_STATE = "state";

    /**
     * Las preferencias locales.
     */
    private final transient SharedPreferences mPrefs;
    /**
     * Bloqueo para sincronizar.
     */
    private final transient ReentrantLock mPrefsLock;
    /**
     * Instancia sincronizada del estado.
     */
    private final transient AtomicReference<AuthState> mCurrentAuthState;

    /**
     * Constructor con las propiedades para inyección de dependencias.
     * @param context el contexto de android
     */
    @Inject
    public AuthStateManager(final Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mPrefsLock = new ReentrantLock();
        mCurrentAuthState = new AtomicReference<>();
    }

    /**
     * Lee el estado actual.
     * @return el estado actual
     */
    @AnyThread
    @NonNull
    public AuthState getCurrent() {
        AuthState authState;
        if (mCurrentAuthState.get() == null) {
            authState = readState();
            if (!mCurrentAuthState.compareAndSet(null, authState)) {
                authState = mCurrentAuthState.get();
            }
        } else {
            authState = mCurrentAuthState.get();
        }
        return authState;
    }

    /**
     * Sobreescribe el estado actual.
     * @param state el nuevo estado
     * @return el nuevo estado
     */
    @AnyThread
    @NonNull
    public AuthState replace(@NonNull final AuthState state) {
        writeState(state);
        mCurrentAuthState.set(state);
        return state;
    }

    /**
     * Actualiza el estado a partir de la respuesta.
     * @param response la respuesta del servicio
     * @param ex si hubo excepción
     * @return el nuevo estado
     */
    @AnyThread
    @NonNull
    public AuthState updateAfterAuthorization(
            @Nullable final AuthorizationResponse response,
            @Nullable final AuthorizationException ex) {
        final AuthState current = getCurrent();
        current.update(response, ex);
        return replace(current);
    }

    /**
     * Actualiza el estado a partir de la respuesta.
     * @param response la respuesta del servicio
     * @param ex si hubo excepción
     * @return el nuevo estado
     */
    @AnyThread
    @NonNull
    public AuthState updateAfterTokenResponse(
            @Nullable final TokenResponse response,
            @Nullable final AuthorizationException ex) {
        final AuthState current = getCurrent();
        current.update(response, ex);
        return replace(current);
    }

    /**
     * Actualiza el estado a partir de la respuesta.
     * @param response la respuesta del servicio
     * @param ex si hubo excepción
     * @return el nuevo estado
     */
    @AnyThread
    @NonNull
    public AuthState updateAfterRegistration(
            final RegistrationResponse response,
            final AuthorizationException ex) {
        AuthState current = getCurrent();
        if (ex == null) {
            current.update(response);
            current = replace(current);
        }
        return current;
    }

    /**
     * Lee el estado desde el almacenamiento.
     * @return el estado leído
     */
    @AnyThread
    @NonNull
    private AuthState readState() {
        AuthState authState;
        mPrefsLock.lock();
        try {
            final String currentState = mPrefs.getString(KEY_STATE, null);
            if (currentState == null) {
                authState = new AuthState();
            } else {
                try {
                    authState = AuthState.jsonDeserialize(currentState);
                } catch (final JSONException ex) {
                    AppLog.error(TAG, ex, "No fue posible deserializar el estado %s", currentState);
                    authState = new AuthState();
                }
            }
        } finally {
            mPrefsLock.unlock();
        }
        return authState;
    }

    /**
     * Escribe el estado al almacenamiento.
     * @param state el estado actual
     */
    @AnyThread
    private void writeState(@Nullable final AuthState state) {
        mPrefsLock.lock();
        try {
            final SharedPreferences.Editor editor = mPrefs.edit();
            if (state == null) {
                editor.remove(KEY_STATE);
            } else {
                editor.putString(KEY_STATE, state.jsonSerializeString());
            }

            if (!editor.commit()) {
                throw new IllegalStateException("Failed to write state to shared prefs");
            }
        } finally {
            mPrefsLock.unlock();
        }
    }
}
