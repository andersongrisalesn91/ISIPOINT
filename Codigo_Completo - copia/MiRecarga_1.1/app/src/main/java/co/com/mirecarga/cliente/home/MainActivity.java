package co.com.mirecarga.cliente.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

//import androidx.drawerlayout.widget.DrawerLayout;
import co.com.mirecarga.core.util.AdvanceDrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import co.com.mirecarga.cliente.R;
import co.com.mirecarga.cliente.login.LoginClienteFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.MenuGrupo;
import co.com.mirecarga.core.home.MenuOpcion;
import co.com.mirecarga.core.home.NavegadorListener;
import co.com.mirecarga.core.home.PaginaNavegacion;
import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.core.login.ProveedorAutenticacion;
import co.com.mirecarga.core.util.AppException;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.LogFragmentLifecycleCallbacks;
import dagger.android.AndroidInjection;

/**
 * Actividad para el home del vendedor.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavegadorListener {
    /**
     * El tag para el log.
     */
    private static final String TAG = "MainActivity";

    /**
     * Constantes para ids de los menús.
     */
    private static final int MNU_INICIO = 100;
    /**
     * Constantes para ids de los menús.
     */
    private static final int MNU_CERRAR_SESION = 101;

    /**
     * Servicio de la actividad principal.
     */
    @Inject
    transient HomeService homeService;

    /**
     * Respuesta del servicio de inicio con el menú.
     */
    private transient RespuestaInicio modelo;

    /**
     * Icono para mostrar el menú.
     */
    private transient ActionBarDrawerToggle toggle;

    /**
     * Servicio de ejecución de código asíncrono.
     */
    @Inject
    transient ExecutorService executor;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new LogFragmentLifecycleCallbacks(), true);
        if (savedInstanceState == null) {
            mostrarPaginaInicial();
        } else {
            AppLog.debug(TAG, "Recuperando en onCreate %s", fragmentManager.getFragments());
            for (final Fragment fragment : fragmentManager.getFragments()) {
                fragmentManager.getFragment(savedInstanceState, fragment.getClass().getSimpleName());
            }
        }

        final AdvanceDrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        llenarMenu();
    }

    /**
     * Muestra la página inicial dependiendo de si el usuario está autenticado o no.
     */
    private void mostrarPaginaInicial() {
        final RespuestaInicio resp = getModelo();
        if (resp.isAutenticado()) {
            remplazar(new MainFragment());
        } else {
            remplazar(new LoginClienteFragment());
        }
    }

    @Override
    public final void setTitulo(final String titulo) {
        if (!TextUtils.isEmpty(titulo)) {
            final ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(titulo + " v2.0.18c");
            }
        }
    }

    @Override
    public final Fragment navegar(final Class fragmentClass, final String titulo) {
        Fragment fragment = null;
        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                if (fragment instanceof PaginaNavegacion) {
                    ((PaginaNavegacion) fragment).setTitulo(titulo);
                }
                navegar(fragment);
            } catch (final InstantiationException e1) {
                AppLog.error(TAG, e1, "No es posible instanciar %s", fragmentClass);
            } catch (final IllegalAccessException e1) {
                AppLog.error(TAG, e1, "No es posible acceder a %s", fragmentClass);
            }
        }
        return fragment;
    }

    /**
     * Muestra el fragmento indicado por la instancia.
     * @param fragment la instancia del fragmento
     * @param remplazar indica si agregarlo a la pila
     */
    private void navegar(final Fragment fragment, final boolean remplazar) {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment);
        if (!remplazar) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public final void navegar(final Fragment fragment) {
        navegar(fragment, false);
    }

    @Override
    public final void navegar(final int id) {
        irInicio();

        if (id == MNU_CERRAR_SESION) {
            cerrarSesion();
        } else {
            // Instancia la clase
            final RespuestaInicio resp = getModelo();
            final MenuOpcion opcion = resp.getOpcionPorId(id);
            if (opcion != null && !TextUtils.isEmpty(opcion.getClase())) {
                try {
                    final Class clazz = Class.forName(opcion.getClase());
                    navegar(clazz, opcion.getNombre());
                } catch (final ClassNotFoundException e) {
                    AppLog.error(TAG, e, "No fue posible instanciar %s", opcion.getClase());
                }
            }
        }
    }

    @Override
    public void remplazar(final Fragment fragment) {
        navegar(fragment, true);
    }

    @Override
    public void irAtras() {
        final AdvanceDrawerLayout drawer = findViewById(R.id.drawer_layout);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        AppLog.debug(TAG, "irAtras Stack %s", fragmentManager.getBackStackEntryCount());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void irInicio() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        // Regresa a la página de inicio sin cerrarla (flag 0)
        fragmentManager.popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void actualizarModelo() {
        modelo = homeService.consultarModelo();
        invalidateOptionsMenu();
        llenarMenu();
        mostrarPaginaInicial();
    }

    @Override
    public void cerrarSesion() {
        homeService.cerrarSesion();
        actualizarModelo();
        executor.submit(this::cerrarSesionProveedorAutenticacion);
    }

    @Override
    public void mostrarProcesando(final boolean mostrar) {
        final View viewById = findViewById(R.id.progressbar);
        if (mostrar) {
            viewById.setVisibility(View.VISIBLE);
        } else {
            viewById.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPermitirFiltrar(final boolean permitirFiltrar) {
        // No aplica
    }

    /**
     * Cierra la sesión en el proveedor externo.
     */
    private void cerrarSesionProveedorAutenticacion() {
        final ProveedorAutenticacion proveedorAutenticacion = getModelo().getProveedorAutenticacion();
        switch (proveedorAutenticacion) {
            case Facebook:
                com.jaychang.sa.facebook.SimpleAuth.disconnectFacebook();
                break;
            case Twitter:
                com.jaychang.sa.twitter.SimpleAuth.disconnectTwitter();
                break;
            case Google:
                com.jaychang.sa.google.SimpleAuth.disconnectGoogle();
                break;
            default:
                throw new AppException("Proveedor no reconocido %s", proveedorAutenticacion);
        }
    }

    /**
     * Pinta el menú dinámico de la aplicación.
     */
    private void llenarMenu() {
        final RespuestaInicio resp = getModelo();
        final boolean autenticado = resp.isAutenticado();
        toggle.setDrawerIndicatorEnabled(autenticado);
        if (autenticado) {
            final NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            final View hView = navigationView.getHeaderView(0);
            //Establece nombre completo del usuario en panel navegación
            final TextView navegacionNombreUsuario = hView.findViewById(R.id.navegacionNombreUsuario);
            navegacionNombreUsuario.setText(resp.getNombreCompleto());
            //Establece correo electrónico del usuario en panel navegación
            final TextView navegacionCorreoUsuario = hView.findViewById(R.id.navegacionCorreoUsuario);
            navegacionCorreoUsuario.setText(resp.getCorreoElectronico());

            final Menu menu = navigationView.getMenu();
            menu.clear();
            //Agrega item inicio
            menu.add(Menu.NONE, MNU_INICIO, Menu.NONE, R.string.navigation_inicio).setIcon(R.drawable.ic_home_black_24dp);

            //Agrega grupo
            for (final MenuGrupo grupo : resp.getMenu()) {
                final SubMenu subMenuActual = menu.addSubMenu(grupo.getNombre());
                int cont = 0;
                //Agrega item
                for (final MenuOpcion item : grupo.getItems()) {
                    cont++;
                    final int idIcono = getResources().getIdentifier(item.getIcono(), "drawable", getPackageName());
                    final Drawable drawableIcono = getResources().getDrawable(idIcono);
                    subMenuActual.add(Menu.NONE, item.getId(), cont, item.getNombre()).setIcon(drawableIcono);
                }
            }

            //Agrega item cerrar sesión
            menu.add(Menu.NONE, MNU_CERRAR_SESION, Menu.NONE, R.string.navigation_salir).setIcon(R.drawable.ic_menu_exit);
        }
    }

    /**
     * Procesa el evento onBackPressed.
     */
    @Override
    public void onBackPressed() {
        irAtras();
    }

    /**
     * Procesa el evento onCreateOptionsMenu.
     * @param menu el menú
     * @return verdadero
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Procesa el evento onNavigationItemSelected.
     * @param item el item
     * @return verdadero
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        navegar(id);
        final AdvanceDrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Obtiene los datos de la variable en memoria o del servicio.
     * @return los datos de la página
     */
    private RespuestaInicio getModelo() {
        if (modelo == null) {
            modelo = homeService.consultarModelo();
        }
        return modelo;
    }

    @Override
    protected final void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        final FragmentManager fragmentManager = getSupportFragmentManager();
        AppLog.debug(TAG, "onSaveInstanceState %s", fragmentManager.getFragments());
        for (final Fragment fragment : fragmentManager.getFragments()) {
            fragmentManager.putFragment(outState, fragment.getClass().getSimpleName(), fragment);
        }
    }
}
