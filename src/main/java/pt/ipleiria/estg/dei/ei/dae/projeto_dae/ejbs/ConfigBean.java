package pt.ipleiria.estg.dei.ei.dae.projeto_dae.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projeto_dae.entities.Package;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private CarrierBean carrierBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private OrderCheckpointBean orderCheckpointBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private PackageTypeBean packageTypeBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private ProductTypeBean productTypeBean;

    @EJB
    private ProductEntryBean productEntryBean;

    @EJB
    private ManufacturerBean manufacturerBean;

    @PostConstruct
    public void populateDB() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        //carriers
        Carrier carrier1 = null;
        Carrier carrier2 = null;
        Carrier carrier3 = null;

        try {
            carrier1 = carrierBean.create("CTT");
            carrier2 = carrierBean.create("TAP");
            carrier3 = carrierBean.create("DPD");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //manufacturers
        Manufacturer manufacturer1 = null;
        Manufacturer manufacturer2 = null;
        try {
            manufacturer1 = manufacturerBean.create("APPLE");
            manufacturer2 = manufacturerBean.create("SONY");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //product types
        ProductType productType1 = null;
        ProductType productType2 = null;
        ProductType productType3 = null;
        try {
            productType1  = productTypeBean.create("food");
            productType2 = productTypeBean.create("electronics");
            productType3 = productTypeBean.create("musical_instruments");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //package types
        PackageType packageType1 = null;
        PackageType packageType2 = null;
        PackageType packageType3 = null;
        try {
            packageType1 = packageTypeBean.create("regular");
            packageType2 = packageTypeBean.create("transportation");
            packageType3 = packageTypeBean.create("secure");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //packages
        Package package1 = null;
        Package package2 = null;
        Package package3 = null;
        try {
            package1 = packageBean.create("titanium", 1, LocalDate.parse("12/02/2023", dateFormatter));
            package2 = packageBean.create("metal", 1, LocalDate.parse("25/03/2023", dateFormatter));
            package3 = packageBean.create("cardboard", 1, LocalDate.parse("05/10/2023", dateFormatter));
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            packageBean.addPackageType(package1.getId(), packageType1.getId());
            packageBean.addPackageType(package1.getId(), packageType2.getId());
            packageBean.addPackageType(package2.getId(), packageType1.getId());
            packageBean.addPackageType(package2.getId(), packageType3.getId());
            packageBean.addPackageType(package3.getId(), packageType3.getId());
            packageBean.addPackageType(package3.getId(), packageType2.getId());
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //default products
        ProductEntry productEntry1 = null;
        ProductEntry productEntry2 = null;
        ProductEntry productEntry3 = null;
        try {
            productEntry1 = productEntryBean.create("baterias AA", productType2.getId(), 0.08F, manufacturer1.getId(),
                    "copper,steel");
            productEntry2 = productEntryBean.create("pao", productType1.getId(), 0.08F, manufacturer2.getId(),
                    "cereals");
            productEntry3 = productEntryBean.create("burger", productType1.getId(), 0.08F, manufacturer2.getId(),
                    "cow_meat,bread,lettuce,mayo");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //products
        Product product1 = null;
        Product product2 = null;
        try {
            product1 = productBean.create(productEntry1.getId(), 1F, null);
            product2 = productBean.create(productEntry1.getId(), 1F, null);
            productBean.create(productEntry2.getId(), 1f, LocalDate.parse("05/10/2023", dateFormatter));
            productBean.create(productEntry2.getId(), 1f, LocalDate.parse("07/10/2023", dateFormatter));
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //orders
        Order order1 = null;
        try {
            order1 = orderBean.create(LocalDate.parse("05/10/2023", dateFormatter), null,
                    "Rua de um Armazem na China", "Rua qualquer nos USA");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            orderBean.addProduct(order1.getId(), product1.getId());
            orderBean.addProduct(order1.getId(), product2.getId());
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        //order checkpoints
        try {
            orderCheckpointBean.create(order1.getId(), package1.getId(), carrier1.getId(), "Aeroporto China",
                    LocalDate.parse("05/10/2023 15:20:12", dateTimeFormatter), -5f, 3f,
                    0.4f, 0.6f, 1000f, 1010f, 5f,
                    false);
            orderCheckpointBean.create(order1.getId(), package1.getId(), carrier2.getId(), "Aeroporto USA",
                    LocalDate.parse("06/10/2023 08:15:00", dateTimeFormatter), -5f, 3f,
                    0.4f, 0.6f, 1000f, 1010f, 5f,
                    false);
            orderCheckpointBean.create(order1.getId(), package1.getId(), carrier3.getId(), "Casa do bro",
                    LocalDate.parse("08/10/2023 12:15:00", dateTimeFormatter), -5f, 3f,
                    0.4f, 0.6f, 1000f, 1010f, 5f,
                    false);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            orderBean.setOrderDeliveryDateTime(order1.getId(), LocalDate.parse("08/10/2023 12:15:00", dateTimeFormatter));
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

}
