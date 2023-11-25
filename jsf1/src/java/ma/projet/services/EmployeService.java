/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.services;

import ma.projet.beans.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Employe;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author LACHGAR
 */
public class EmployeService extends AbstractFacade<Employe> {

    @Override
    protected Class<Employe> getEntityClass() {
        return Employe.class;
    }

    public List<Object[]> nbemploye() {
        List<Object[]> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("select count(m.marque), m.marque from Employe m group by m.marque").list();
        session.getTransaction().commit();
        return employes;
    }

    public List<Employe> getbydates(Date d1, Date d2) {
        List<Employe> employes = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employes = session.createQuery("from Employe m where m.dateAchat between :d1 and :d2").setParameter("d1", d1).setParameter("d2", d2).list();
        session.getTransaction().commit();
        return employes;

    }

    public List<Object[]> nbEmployeByChef() {
        List<Object[]> Employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employes = session.createQuery("select count(e.nom), e.chef.nom from Employe e group by e.chef.nom").list();
        session.getTransaction().commit();
        return Employes;
    }

    public List<Employe> getCollaborateurs(Service service) {
        List<Employe> collaborateurs;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        collaborateurs = session.createQuery("SELECT e FROM Employe e JOIN e.service s WHERE s.id = :serviceId AND e.chef IS NOT NULL")
                .setParameter("serviceId", service.getId())
                .list();

        session.getTransaction().commit();
        return collaborateurs;
    }

    public List<Employe> getChef(Service service) {
        List<Employe> collaborateurs;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        collaborateurs = session.createQuery("SELECT e FROM Employe e JOIN e.service s WHERE s.id = :serviceId AND e.chef IS NULL")
                .setParameter("serviceId", service.getId())
                .list();

        session.getTransaction().commit();
        return collaborateurs;
    }
}
