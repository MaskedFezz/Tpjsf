/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ma.projet.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Service;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author HP
 */
public class ServiceService extends AbstractFacade<Service> {

    @Override
    protected Class<Service> getEntityClass() {
        return Service.class;
    }
    
    public List<Object[]> nbservice(){
        List<Object[]> services = null;
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        services  = session.createQuery("select count(m.marque), m.marque from Service m group by m.marque").list();
        session.getTransaction().commit();
        return services;
    }
    
    public List<Service> getbydates(Date d1 , Date d2){
        List <Service> services = new ArrayList<>();
        Session session  = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
         services  = session.createQuery("from Service m where m.dateAchat between :d1 and :d2").setParameter("d1", d1).setParameter("d2", d2).list();
        session.getTransaction().commit();
        return services;
        
    }
    
}
