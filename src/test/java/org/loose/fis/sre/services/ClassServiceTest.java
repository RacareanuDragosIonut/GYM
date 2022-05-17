package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.Classdoesnotexist;
import org.loose.fis.sre.exceptions.Classwiththatscheduledoesnotexist;
import org.loose.fis.sre.exceptions.DayandHourTaken;

import org.loose.fis.sre.model.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.loose.fis.sre.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;
public class ClassServiceTest {
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }
    @BeforeEach
    void setUp()throws SQLException{
         DatabaseConnection connectNow = new DatabaseConnection();
         Connection connection = connectNow.getConnection();
        connection = DriverManager.getConnection("jdbc:mysql://localhost/gymapp", "root","douazecisiunu2121" );
        PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM classes");
        System.out.println(preparedStatement);
        preparedStatement.executeUpdate();
    }
    @AfterEach
    void tearDown() {
        System.out.println("After each");
    }

    @Test
    @DisplayName("Classes Table is empty")
    void testTableisInitialisedandNoClassisPersisted(){
       assertThat(ClassService.getAllClasses()).isNull();
    }
    @Test
    @DisplayName("Class is successfully added to the table")
    void testClassIsAddedToTable() throws DayandHourTaken, SQLException {
        ClassService.addClass("Upper Body Class","Tuesday", "12",0);
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        Class gymclass = ClassService.getAllClasses().get(0);
        assertThat(gymclass).isNotNull();
        assertThat(gymclass.gettype()).isEqualTo("Upper Body Class");
        assertThat(gymclass.getday()).isEqualTo("Tuesday");
        assertThat(gymclass.gethour()).isEqualTo("12");
        assertThat(gymclass.getnumberofclients()).isEqualTo(0);

    }
    @Test
    @DisplayName("Class with the same schedule as another can't be added to the table afterwards")
    void testClasswithTheSameScheduleCanNotBeAddedTwice()throws SQLException,DayandHourTaken{
        ClassService.addClass("Upper Body Class","Tuesday", "12",0);
        assertThrows(DayandHourTaken.class,()->{
            ClassService.addClass("Lower Body Class","Tuesday","12",0);
        });
    }
    @Test
    @DisplayName("Class schedule is successfully edited")
    void testClassIsEditedintheTable()throws Classwiththatscheduledoesnotexist,SQLException,DayandHourTaken{
        ClassService.addClass("Upper Body Class","Tuesday","12",0);
        ClassService.editClass("Lower Body Class","Tuesday","12",0);
        assertThat(ClassService.getAllClasses()).isNotEmpty();
        assertThat(ClassService.getAllClasses()).size().isEqualTo(1);
        Class gymclass = ClassService.getAllClasses().get(0);
        assertThat(gymclass).isNotNull();
        assertThat(gymclass.gettype()).isEqualTo("Lower Body Class");
        assertThat(gymclass.getday()).isEqualTo("Tuesday");
        assertThat(gymclass.gethour()).isEqualTo("12");
        assertThat(gymclass.getnumberofclients()).isEqualTo(0);

    }
    @Test
    @DisplayName("Class with other schedule than every other class does not edit everything,in order to edit a class you have to introduce the same day and hour to the new edited class")
    void testClasswithDistinctScheduleDoesNotChangeAnything()throws SQLException,DayandHourTaken{
        ClassService.addClass("Upper Body Class","Tuesday","12",0);
        assertThrows(Classwiththatscheduledoesnotexist.class,()->{
            ClassService.editClass("Lower Body Class","Wednesday","12",0);
        });
    }
    @Test
    @DisplayName("Class is successfully deleted")
    void testClassisCanceled()throws Classdoesnotexist,SQLException,DayandHourTaken{
        ClassService.addClass("Lower Body Class","Tuesday","12",0);
        ClassService.cancelClass("Lower Body Class","Tuesday","12",0);
        assertThat(ClassService.getAllClasses()).isNull();
    }
    @Test
    @DisplayName("Class to be canceled can not be found")
    void testClasstobeCanceledCanNotBeFound()throws SQLException,DayandHourTaken{
        ClassService.addClass("Lower Body Class","Tuesday","12",0);

        assertThrows(Classdoesnotexist.class,()->{
            ClassService.cancelClass("Upper Body Class","Tuesday","12",0);
        });
    }

}
