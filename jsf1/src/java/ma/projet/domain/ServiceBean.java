package ma.projet.domain;

import java.util.List;
import javax.faces.bean.ManagedBean;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;

import ma.projet.services.ServiceService;
import ma.projet.services.EmployeService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "serviceBean")
public class ServiceBean {

    private Employe Employe;
    private Service service;
    private ServiceService serviceService;
    private List<Service> services;
    private List<Employe> Employes;
    private EmployeService EmployeService;
    private static ChartModel barModel;
    private Employe chef;
 private TreeNode root;
    public ServiceBean() {
        service = new Service();
        serviceService = new ServiceService();
        Employe = new Employe();
        EmployeService = new EmployeService();
    }

    public List<Employe> getEmployes() {
        if (Employes == null) {
            Employes = service.getEmployes();
        }
        return Employes;
    }

    public void setEmployes(List<Employe> Employes) {
        this.Employes = Employes;
    }

    public List<Service> getServices() {
        if (services == null) {
            services = serviceService.getAll();
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String onCreateAction() {
        serviceService.create(service);
        service = new Service();
        return null;
    }

    public void onDeleteAction() {
        service.setEmployes(null);
        serviceService.delete(service);
    }

    public void onEdit(RowEditEvent event) {
        service = (Service) event.getObject();
        service.setEmployes(null);
        serviceService.update(service);
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getEmployes();
    }

    public void onCancel(RowEditEvent event) {
    }

    public void onEditm(RowEditEvent event) {
        Employe = (Employe) event.getObject();
        Service service = serviceService.getById(this.Employe.getService().getId());
        Employe.setService(service);
        Employe.getService().setNom(service.getNom());
        EmployeService.update(Employe);
    }

    public String onDeleteActionm() {
        EmployeService.delete(EmployeService.getById(Employe.getId()));
        return null;
    }

    public List<Employe> serviceLoad() {
        for (Employe e : EmployeService.getAll()) {
            if (e.getService().equals(service)) {
                Employes.add(e);
            }
        }
        return Employes;
    }

    public void onCancelm(RowEditEvent event) {
    }

 
    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries services = new ChartSeries();
        services.setLabel("Services");
        model.setAnimate(true);
        for (Service s : serviceService.getAll()) {
            services.set(s.getNom(), s.getEmployes().size());
        }
        model.addSeries(services);

        return model;
    }
      public void loadChef() {
        System.out.println(service);
        chef = (Employe) EmployeService.getChef(service);
    }

    public Employe getEmploye() {
        return Employe;
    }

    public void setEmploye(Employe Employe) {
        this.Employe = Employe;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public EmployeService getEmployeService() {
        return EmployeService;
    }

    public void setEmployeService(EmployeService EmployeService) {
        this.EmployeService = EmployeService;
    }

    public Employe getChef() {
        return chef;
    }

    public void setChef(Employe chef) {
        this.chef = chef;
    }
//  public TreeNode getRoot() {
//        TreeNode root = new DefaultTreeNode("root", null);
//
//        for (Service service : services) {
//            TreeNode serviceNode = new DefaultTreeNode(service, root);
//
//            // Load chief under service
//             chef = (Employe) EmployeService.getChef(service);
//            if (chef != null) {
//                TreeNode chefNode = new DefaultTreeNode(chef, serviceNode);
//            }
//
//            // Load employees under service
//            List<Employe> employees = EmployeService.getCollaborateurs(service);
//            for (Employe employee : employees) {
//                TreeNode employeNode = new DefaultTreeNode(employee, serviceNode);
//            }
//        }
//
//        return root;
//    }
public void loadTree() {
        root.getChildren().clear(); // Clear old nodes

        for (Service service : services) {
            // Create a node for the service
            TreeNode serviceNode = new DefaultTreeNode(service, root);

            // Display chief under service
            Employe chief = EmployeService.getChef(service).get(0);
            if (chief != null) {
                // Create a node for the chief under the service node
                TreeNode chiefNode = new DefaultTreeNode(chief, serviceNode);

                // Display employees under chief
                for (Employe employe : service.getEmployes()) {
                    // Create a node for each employee under the chief node
                    TreeNode employeNode = new DefaultTreeNode(employe , chiefNode);
                }

            }
        }
    }
}