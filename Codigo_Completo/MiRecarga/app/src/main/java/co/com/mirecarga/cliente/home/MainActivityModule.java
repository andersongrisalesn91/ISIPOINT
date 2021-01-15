package co.com.mirecarga.cliente.home;

import co.com.mirecarga.cliente.login.LoginClienteFragment;
import co.com.mirecarga.cliente.mapa.FiltroMapaFragment;
import co.com.mirecarga.cliente.mapa.MapaClienteFragment;
import co.com.mirecarga.cliente.mapa.MapaClienteService;
import co.com.mirecarga.cliente.mapa.MapaClienteServiceBean;
import co.com.mirecarga.cliente.placaadmin.PlacaAdminFragment;
import co.com.mirecarga.cliente.placaadmin.PlacaAdminService;
import co.com.mirecarga.cliente.placaadmin.PlacaAdminServiceBean;
import co.com.mirecarga.cliente.recarga.CompraPaqueteFragment;
import co.com.mirecarga.cliente.recarga.CompraPaqueteService;
import co.com.mirecarga.cliente.recarga.CompraPaqueteServiceBean;
import co.com.mirecarga.cliente.recarga.RecargaSaldoFragment;
import co.com.mirecarga.cliente.reportes.TransaccionesPorPinFragment;
import co.com.mirecarga.cliente.reportes.TransaccionesPorPinService;
import co.com.mirecarga.cliente.reportes.TransaccionesPorPinServiceBean;
import co.com.mirecarga.cliente.reportes.TransaccionesRealizadasFragment;
import co.com.mirecarga.cliente.tarjetas.ListaTarjetasEnUsoFragment;
import co.com.mirecarga.cliente.tarjetas.ListaTarjetasFragment;
import co.com.mirecarga.cliente.venta.FinalizarUsoPinFragment;
import co.com.mirecarga.cliente.venta.FinalizarUsoPinService;
import co.com.mirecarga.cliente.venta.FinalizarUsoPinServiceBean;
import co.com.mirecarga.cliente.venta.InicioUsoPinFragment;
import co.com.mirecarga.cliente.venta.InicioUsoPinService;
import co.com.mirecarga.cliente.venta.InicioUsoPinServiceBean;
import co.com.mirecarga.cliente.ventamapa.ConfirmarVentaMapaFragment;
import co.com.mirecarga.cliente.ventamapa.VentaMapaFragment;
import co.com.mirecarga.cliente.ventamapa.VentaMapaService;
import co.com.mirecarga.cliente.ventamapa.VentaMapaServiceBean;
import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.HomeServiceBean;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Módulo Dagger para inyección en la funcionalidad de la página inicial.
 */
@Module()
@SuppressWarnings("PMD.AbstractNaming")
public abstract class MainActivityModule {
    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract MainFragment getMainFragment();
    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract HomeService getHomeService(HomeServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract LoginClienteFragment getLoginClienteFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract PlacaAdminFragment getPlacaAdminFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract PlacaAdminService getPlacaAdminService(PlacaAdminServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract TransaccionesRealizadasFragment getTransaccionesRealizadasFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ListaTarjetasFragment getListaTarjetasFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract InicioUsoPinFragment getInicioUsoPinFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract InicioUsoPinService getInicioUsoPin(InicioUsoPinServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ListaTarjetasEnUsoFragment getListaTarjetasEnUsoFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract TransaccionesPorPinFragment getTransaccionesPorPinFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract TransaccionesPorPinService getTransaccionesPorPin(TransaccionesPorPinServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract FinalizarUsoPinFragment getFinalizarUsoPinFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract FinalizarUsoPinService getFinalizarUsoPin(FinalizarUsoPinServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract RecargaSaldoFragment getRecargaSaldoFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract CompraPaqueteFragment getCompraPaqueteFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract CompraPaqueteService getCompraPaquete(CompraPaqueteServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract MapaClienteFragment getMapaVendedorFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract MapaClienteService getMapaVendedorService(MapaClienteServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentaMapaFragment getVentaMapaFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract VentaMapaService getVentaMapaService(VentaMapaServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfirmarVentaMapaFragment getConfirmarVentaMapaFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract FiltroMapaFragment getFiltroMapaFragment();

}
