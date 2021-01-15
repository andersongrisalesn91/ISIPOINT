package co.com.mirecarga.vendedor.home;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import javax.inject.Inject;

import co.com.mirecarga.core.home.AbstractAppFragment;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.MenuGrupo;
import co.com.mirecarga.core.home.MenuOpcion;
import co.com.mirecarga.core.home.NavegadorListener;
import co.com.mirecarga.core.offline.NetworkStateReceiver;
import co.com.mirecarga.core.offline.NetworkStateReceiver.NetworkStateReceiverListener;
import co.com.mirecarga.core.home.PaginaNavegacion;
import co.com.mirecarga.core.home.RespuestaInicio;
import co.com.mirecarga.core.util.AppLog;
import co.com.mirecarga.core.util.LogFragmentLifecycleCallbacks;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.barcode.BarcodeCaptureActivity;
import co.com.mirecarga.vendedor.barcode.DatosCeldaFragment;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraFragment;
import co.com.mirecarga.vendedor.login.LoginVendedorFragment;
import co.com.mirecarga.vendedor.mapa.FiltroMapaFragment;
import co.com.mirecarga.vendedor.offline.VendedorOfflineService;
import dagger.android.AndroidInjection;

/**
 * Actividad para el home del vendedor.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavegadorListener,
        NetworkStateReceiverListener {
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
     * Constante para recibir la respuesta del código de barras.
     */
    private static final int BARCODE_READER_REQUEST_CODE = 100;

    /**
     * Servicio de la actividad principal.
     */
    @Inject
    transient HomeService homeService;

    /**
     * Servicio de la actividad principal.
     */
    @Inject
    transient VendedorOfflineService vendedorOfflineService;

    /**
     * Respuesta del servicio de inicio con el menú.
     */
    private transient RespuestaInicio modelo;

    /**
     * Icono para mostrar el menú.
     */
    private transient ActionBarDrawerToggle toggle;

    /**
     * Item de menú para filtrar.
     */
    private transient MenuItem menuOpcionFiltrar;

    /**
     * Item de menú para leer código.
     */
    private transient MenuItem menuLeerCodigoQr;

    /**
     * Manejador de eventos de conectividad.
     */
    private transient NetworkStateReceiver networkStateReceiver;

    /**
     * Título actual de la página.
     */
    private transient String tituloApp;

    /**
     * Indica si se encuentra fuera de línea.
     */
    private transient boolean offline;

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

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        llenarMenu();
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Muestra la página inicial dependiendo de si el usuario está autenticado o no.
     */
    private void mostrarPaginaInicial() {
        final RespuestaInicio resp = getModelo();
        if (resp.isAutenticado()) {
            remplazar(new MainFragment());
        } else {
            remplazar(new LoginVendedorFragment());
        }
    }

    @Override
    public final void setTitulo(final String titulo) {
        if (!TextUtils.isEmpty(titulo)) {
            this.tituloApp = titulo;
            final ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                StringBuilder buffer = new StringBuilder(titulo);
                if (offline) {
                    buffer.append(" (Desconectado)");
                }
                buffer.append(" v2.1.2");
                supportActionBar.setTitle(buffer.toString());
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
     *
     * @param fragment  la instancia del fragmento
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
        actualizarMenuOpciones(fragment);
    }

    /**
     * Actualiza el estado del menú de opciones.
     *
     * @param fragment el fragmento actual
     */
    private void actualizarMenuOpciones(final Fragment fragment) {
        if (fragment != null) {
            final RespuestaInicio resp = getModelo();
            if (menuOpcionFiltrar != null) {
                if (fragment instanceof PaginaNavegacion) {
                    final boolean permitirFiltrar = ((PaginaNavegacion) fragment).isPermitirFiltrar();
                    menuOpcionFiltrar.setVisible(permitirFiltrar && resp.isAutenticado());
                } else {
                    menuOpcionFiltrar.setVisible(false);
                }
            }
            if (menuLeerCodigoQr != null) {
                menuLeerCodigoQr.setVisible(resp.isAutenticado());
            }
        }
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
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        if (menuOpcionFiltrar != null) {
            menuOpcionFiltrar.setVisible(permitirFiltrar);
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
     *
     * @param menu el menú
     * @return verdadero
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Muestra / oculta el menú de configuración
        final RespuestaInicio resp = getModelo();
        final MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(resp.isAutenticado());
        menuOpcionFiltrar = menu.findItem(R.id.filtrar);
        menuLeerCodigoQr = menu.findItem(R.id.leercodigoqr);
        actualizarMenuOpciones(getCurrentFragment());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        final boolean resp;
        switch (id) {
            case R.id.action_settings:
                resp = true;
                navegar(new ConfiguracionImpresoraFragment());
                break;
            case R.id.filtrar:
                resp = true;
                navegar(new FiltroMapaFragment());
                break;
            case R.id.leercodigoqr:
                resp = true;
                leerCodigoQr();
                break;
            default:
                resp = super.onOptionsItemSelected(item);
                break;
        }
        return resp;
    }

    /**
     * Lee el código QR.
     */
    private void leerCodigoQr() {
        final Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    /**
     * Obtiene la página de navegación actual.
     *
     * @return la página actual
     */
    private AbstractAppFragment getCurrentFragment() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        return (AbstractAppFragment) fragmentManager.getFragments().get(0);
    }

    /**
     * Abre el código QR recibido.
     *
     * @param qrcode el código QR
     */
    public void abrirQrCode(final String qrcode) {
        AppLog.debug(TAG, "Código QR '%s'", qrcode);
        if (qrcode != null && qrcode.startsWith("t:1;")) {
            final DatosCeldaFragment datosCeldaFragment = new DatosCeldaFragment();
            datosCeldaFragment.leerQrCode(qrcode);
            navegar(datosCeldaFragment);
        } else {
            AppLog.debug(TAG, "El código QR no se reconoce: '%s'", qrcode);
            getCurrentFragment().mostrarMensaje(getString(R.string.qrcode_invalido));
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLog.debug(TAG, "onActivityResult %s %s %s", requestCode, resultCode, data);
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    String qrcode = barcode.displayValue;
                    abrirQrCode(qrcode);
                } else {
                    AppLog.debug(TAG, "Los datos del código QR están vacíos");
                    getCurrentFragment().mostrarMensaje(getString(R.string.operacion_cancelada));
                }
            } else {
                AppLog.debug(TAG, "Operación cancelada");
                getCurrentFragment().mostrarMensaje(getString(R.string.operacion_cancelada));
            }
        }
    }

    /**
     * Procesa el evento onNavigationItemSelected.
     *
     * @param item el item
     * @return verdadero
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        navegar(id);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Obtiene los datos de la variable en memoria o del servicio.
     *
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void networkAvailable() {
        AppLog.debug(TAG, "Conectividad networkAvailable");
        if (offline) {
            offline = false;
            setTitulo(tituloApp);
            vendedorOfflineService.sincronizar();
        }
    }

    @Override
    public void networkUnavailable() {
        AppLog.debug(TAG, "Conectividad networkUnavailable");
        if (!offline) {
            offline = true;
            setTitulo(tituloApp);
        }
    }
}
