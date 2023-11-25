package ma.projet.domain;

import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import ma.projet.beans.Employe;
import ma.projet.beans.Service;

import ma.projet.services.ServiceService;
import ma.projet.services.EmployeService;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean(name = "employeBean")
public class EmployeBean {

    private Employe employe;
    private Service service;
    private List<Employe> employes;
    private List<Employe> employesBetweenDates;
    private EmployeService employeService;
    private ServiceService serviceService;
    private List<Employe> chefs;
    private Employe selectedChef;
    private static ChartModel barModel;
    private Date date1;
    private Date date2;
    private List<Employe> collaborateurs;
    private List<Employe> chef;
    private Service selectedService;

    public EmployeBean() {
        employe = new Employe();
        employe.setService(new Service());
        employeService = new EmployeService();
        serviceService = new ServiceService();
        chefs = employeService.getAll();
        selectedChef = new Employe();
        selectedService = new Service();
    }

    public List<Employe> getemployes() {
        if (employes == null) {
            employes = employeService.getAll();
        }
        return employes;
    }

    public void setemployes(List<Employe> employes) {
        this.employes = employes;
    }

    public EmployeService getEmployeService() {
        return employeService;
    }

    public void setEmployeService(EmployeService employeService) {
        this.employeService = employeService;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public String onCreateAction() {
        EmployeService employeService = new EmployeService();

        // Set the selected chef to the employe
        employe.setChef(selectedChef);

        // Create the employe
        employeService.create(employe);

        // Reset employe and selectedChef
        employe = new Employe();
        selectedChef = null;

        return null;
    }

    public String onDeleteAction() {
        employeService.delete(employeService.getById(employe.getId()));
        return null;
    }

    public List<Employe> serviceLoad() {
        for (Employe e : employeService.getAll()) {
            if (e.getService().equals(service)) {
                employes.add(e);
            }
        }
        return employes;
    }

    public void load() {
        System.out.println(service.getNom());
        service = serviceService.getById(service.getId());
        getemployes();
    }

    public void onEdit(RowEditEvent event) {
        employe = (Employe) event.getObject();
        Service service = serviceService.getById(this.employe.getService().getId());
        employe.setService(service);
        employe.getService().setNom(service.getNom());
        employeService.update(employe);
    }

    public void onCancel(RowEditEvent event) {
    }

    public ChartModel getBarModel() {
        return barModel;
    }

    public ChartModel initBarModel() {
        CartesianChartModel model = new CartesianChartModel();
        ChartSeries employes = new ChartSeries();
        employes.setLabel("employes");
        model.setAnimate(true);

        for (Object[] e : employeService.nbEmployeByChef()) {
            employes.set((String) e[1], Integer.parseInt(e[0].toString()));
        }

        model.addSeries(employes);

        return model;
    }

    public List<Employe> employeLoad() {
        employesBetweenDates = employeService.getbydates(date1, date2);
        return null;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Employe> getEmployesBetweenDates() {
        return employesBetweenDates;
    }

    public void setEmployesBetweenDates(List<Employe> employesBetweenDates) {
        this.employesBetweenDates = employesBetweenDates;
    }

    public List<Employe> getChefs() {
        return chefs;
    }

    public Employe getSelectedChef() {
        return selectedChef;
    }

    public void setSelectedChef(Employe selectedChef) {
        this.selectedChef = selectedChef;
    }

    public void loadCollaborateurs() {
        System.out.println(selectedService);

        collaborateurs = employeService.getCollaborateurs(selectedService);

    }

    public void loadChef() {
        System.out.println(selectedService);
        chef = employeService.getChef(selectedService);
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public ServiceService getServiceService() {
        return serviceService;
    }

    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    public List<Employe> getCollaborateurs() {
        return collaborateurs;
    }

    public void setCollaborateurs(List<Employe> collaborateurs) {
        this.collaborateurs = collaborateurs;
    }

    public List<Employe> getChef() {
        return chef;
    }

    public void setChef(List<Employe> chef) {
        this.chef = chef;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

}
