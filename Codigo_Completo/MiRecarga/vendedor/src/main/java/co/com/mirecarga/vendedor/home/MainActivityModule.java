package co.com.mirecarga.vendedor.home;

import co.com.mirecarga.core.home.HomeService;
import co.com.mirecarga.core.home.HomeServiceBean;
import co.com.mirecarga.core.offline.OfflineDao;
import co.com.mirecarga.core.offline.OfflineRepository;
import co.com.mirecarga.vendedor.barcode.DatosCeldaFragment;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraFragment;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ConfiguracionImpresoraServiceBean;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraService;
import co.com.mirecarga.vendedor.configuracionimpresora.ImpresoraServiceBean;
import co.com.mirecarga.vendedor.login.LoginVendedorFragment;
import co.com.mirecarga.vendedor.mapa.FiltroMapaFragment;
import co.com.mirecarga.vendedor.mapa.MapaVendedorFragment;
import co.com.mirecarga.vendedor.mapa.MapaVendedorService;
import co.com.mirecarga.vendedor.mapa.MapaVendedorServiceBean;
import co.com.mirecarga.vendedor.mensajes.CeldaDao;
import co.com.mirecarga.vendedor.mensajes.CeldaRepository;
import co.com.mirecarga.vendedor.mqtt.MQTTService;
import co.com.mirecarga.vendedor.mqtt.MQTTServiceBean;
import co.com.mirecarga.vendedor.offline.VendedorOfflineService;
import co.com.mirecarga.vendedor.offline.VendedorOfflineServiceBean;
import co.com.mirecarga.vendedor.recargaenlinea.ConfirmarRecargaEnLineaFragment;
import co.com.mirecarga.vendedor.recargaenlinea.RecargaEnLineaFragment;
import co.com.mirecarga.vendedor.recargaenlinea.RecargaEnLineaService;
import co.com.mirecarga.vendedor.recargaenlinea.RecargaEnLineaServiceBean;
import co.com.mirecarga.vendedor.reportes.TransaccionesPinFragment;
import co.com.mirecarga.vendedor.reportes.VentasPorClienteFragment;
import co.com.mirecarga.vendedor.reportes.VentasPorVendedorFragment;
import co.com.mirecarga.vendedor.reportes.VerDetalleFragment;
import co.com.mirecarga.vendedor.ventamapa.ConfirmarVentaMapaFragment;
import co.com.mirecarga.vendedor.ventamapa.HistoricoVentaFragment;
import co.com.mirecarga.vendedor.ventamapa.PostpagoFragment;
import co.com.mirecarga.vendedor.ventamapa.VentaMapaFragment;
import co.com.mirecarga.vendedor.ventamapa.VentaMapaService;
import co.com.mirecarga.vendedor.ventamapa.VentaMapaServiceBean;
import co.com.mirecarga.vendedor.ventapaquetetransito.ConfirmarVentaPaqueteTransitoFragment;
import co.com.mirecarga.vendedor.ventapaquetetransito.VentaPaqueteTransitoFragment;
import co.com.mirecarga.vendedor.ventapaquetetransito.VentaPaqueteTransitoService;
import co.com.mirecarga.vendedor.ventapaquetetransito.VentaPaqueteTransitoServiceBean;
import co.com.mirecarga.vendedor.ventapintransito.ConfirmarVentaPinTransitoFragment;
import co.com.mirecarga.vendedor.ventapintransito.VentaPinTransitoFragment;
import co.com.mirecarga.vendedor.ventapintransito.VentaPinTransitoService;
import co.com.mirecarga.vendedor.ventapintransito.VentaPinTransitoServiceBean;
import co.com.mirecarga.vendedor.ventatarjeta.ConfirmarVentaTarjetaFragment;
import co.com.mirecarga.vendedor.ventatarjeta.VentaTarjetaFragment;
import co.com.mirecarga.vendedor.ventatarjeta.VentaTarjetaService;
import co.com.mirecarga.vendedor.ventatarjeta.VentaTarjetaServiceBean;
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
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract OfflineRepository getOfflineRepository(OfflineDao bean);

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract VendedorOfflineService getVendedorOfflineService(VendedorOfflineServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract LoginVendedorFragment getLoginVendedorFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract RecargaEnLineaFragment getRecargaEnLineaFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract RecargaEnLineaService getRecargaEnLineaService(RecargaEnLineaServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfirmarRecargaEnLineaFragment getConfirmarRecargaEnLineaFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentasPorVendedorFragment getVentasPorVendedorFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract TransaccionesPinFragment getTransaccionesPinFragmentFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentasPorClienteFragment getVentasPorClienteFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VerDetalleFragment getVerDetalleFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentaPaqueteTransitoFragment getVentaPaqueteTransitoFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract VentaPaqueteTransitoService getVentaPaqueteTransitoService(VentaPaqueteTransitoServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfirmarVentaPaqueteTransitoFragment getConfirmarVentaPaqueteTransitoFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfiguracionImpresoraFragment getConfiguracionImpresoraFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract ConfiguracionImpresoraService getConfiguracionImpresoraService(ConfiguracionImpresoraServiceBean bean);

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract MQTTService getMQTTService(MQTTServiceBean bean);

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract CeldaRepository getCeldaRepository(CeldaDao bean);

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract ImpresoraService getImpresoraService(ImpresoraServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract MapaVendedorFragment getMapaVendedorFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract MapaVendedorService getMapaVendedorService(MapaVendedorServiceBean bean);

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
    public abstract PostpagoFragment getPostpagoFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract HistoricoVentaFragment getHistoricoVentaFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract FiltroMapaFragment getFiltroMapaFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract DatosCeldaFragment getDatosCeldaFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentaPinTransitoFragment getVentaPinTransitoFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract VentaPinTransitoService getVentaPinTransitoService(VentaPinTransitoServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfirmarVentaPinTransitoFragment getConfirmarVentaPinTransitoFragment();

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract VentaTarjetaFragment getVentaTarjetaFragment();

    /**
     * Asocia el servicio a la implementación.
     * @param bean la implementación del servicio.
     * @return la instancia creada
     */
    @Binds
    public abstract VentaTarjetaService getVentaTarjetaService(VentaTarjetaServiceBean bean);

    /**
     * Declara el fragmento.
     * @return la instancia del fragmento
     */
    @ContributesAndroidInjector
    public abstract ConfirmarVentaTarjetaFragment getConfirmarVentaTarjetaFragment();
}
