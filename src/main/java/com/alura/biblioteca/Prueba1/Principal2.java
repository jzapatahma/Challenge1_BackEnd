package com.alura.biblioteca.Prueba1;

import com.alura.biblioteca.model.Biblioteca;
import com.alura.biblioteca.principal.Principal;
import com.alura.biblioteca.repository.BibliotecaRepository;
import com.alura.biblioteca.repository.MaestroxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Principal2 {

    //private MaestroxRepository repoMaestro;

//    @Autowired
//    public void Principal2(MaestroxRepository repositoryMaestro) {
//        this.repoMaestro= repositoryMaestro;
//    }
//
//    public void guardarMaestro(Maestro maestro) {
//        repoMaestro.save(maestro);
//    }
//    private static List<Maestro> datosMaestro = new ArrayList<>();
//    @Autowired
//    private static MaestroxRepository repoMaestro;
    @Autowired
    private MaestroxRepository repoMaestro;
    public Principal2(MaestroxRepository repositoryMestro) {
        //this.repoBiblio = repositoryBiblio;
        this.repoMaestro = repositoryMestro;
        Maestro maestro = new Maestro();
        maestro.setNombre("Maestro 1");
        Detalle detalle1 = new Detalle();
        detalle1.setDescripcion("Detalle 1");
        Detalle detalle2 = new Detalle();
        detalle2.setDescripcion("Detalle 2");
// Establecer la relación entre maestro y detalles
        detalle1.setMaestro(maestro);
        detalle2.setMaestro(maestro);
// Agregar detalles a la lista
        maestro.getDetalles().add(detalle1);
        maestro.getDetalles().add(detalle2);
        System.out.println(maestro);

        repoMaestro.save(maestro);
        maestro.setDetalles(maestro.getDetalles());
        //
        repoMaestro.save(maestro);
        
    }

    public static void main(String[] args) throws IOException {
        //MaestroxRepository repoMaestro = null;

//        Maestro maestro = new Maestro();
//        maestro.setNombre("Maestro 1");
//        Detalle detalle1 = new Detalle();
//        detalle1.setDescripcion("Detalle 1");
//        Detalle detalle2 = new Detalle();
//        detalle2.setDescripcion("Detalle 2");
//// Establecer la relación entre maestro y detalles
//        detalle1.setMaestro(maestro);
//        detalle2.setMaestro(maestro);
//// Agregar detalles a la lista
//        maestro.getDetalles().add(detalle1);
//        maestro.getDetalles().add(detalle2);
//        System.out.println(maestro);
//
//        repoMaestro.save(maestro);
//        maestro.setDetalles(maestro.getDetalles());
//        //
//        repoMaestro.save(maestro);
        MaestroxRepository repoMaestro = null;
        Principal2 ppal = new Principal2(repoMaestro);


// Establecer la relación entre maestro y detalles
        //detalle1.setMaestro(maestro);
        //detalle2.setMaestro(maestro);

// Agregar detalles a la lista
        //maestro.getDetalles().add(detalle1);
        //maestro.getDetalles().add(detalle2);

        //System.out.println(maestro);
        //System.out.println(maestro.getDetalles());
        //maestro.getDetalles().stream().toList().forEach(System.out::println);

//        Principal2 principal = new Principal2(repoMaestro);
//        Maestro maest = new Maestro();

        //principal.guardarMaestro(maestro);
        //repoMaestro.save(maest);


//
//        maestro.setDetalles(Arrays.asList(detalle1, detalle2));
//
//        maestro.getDetalles().stream().toList().forEach(System.out::println);
//
//        System.out.println(maestro);
//
//        //repoMaestro.save(maestro);


//// Crear y configurar LocalSessionFactoryBean
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//// Configurar sessionFactoryBean aquí
//        sessionFactoryBean.afterPropertiesSet(); // Inicializar LocalSessionFactoryBean
//        SessionFactory sessionFactory = sessionFactoryBean.getObject(); // Obtener SessionFactory
//
//// Abrir sesión de Hibernate
//        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
//
//// Operaciones de persistencia
//        session.save(maestro);
//
//// Confirmar transacción y cerrar sesión
//        tx.commit();
//        session.close();

    }

}
