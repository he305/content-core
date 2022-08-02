package com.github.he305.contentcore.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.DependencyRules.dependOnUpperPackages;

class DomainArchitectureTest {

    private JavaClasses importedClasses;

    @BeforeEach
    void setUp() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.github.he305");
    }

    @Test
    void test_no_accesses_to_upper_package() {
        ArchRule rule = noClasses().should(dependOnUpperPackages());
        rule.allowEmptyShould(true).check(importedClasses);
    }

    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..controller..")
                .allowEmptyShould(true).check(importedClasses);
    }

    @Test
    void serviceClassesShouldBeNamedXServiceOrXComponentOrXServiceImpl() {
        classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service")
                .orShould().haveSimpleNameEndingWith("ServiceImpl")
                .orShould().haveSimpleNameEndingWith("Component")
                .allowEmptyShould(true).check(importedClasses);
    }

    @Test
    void repositoryClassesShouldBeNamedXRepository() {
        classes()
                .that().resideInAPackage("..repository..")
                .should().haveSimpleNameEndingWith("Repository")
                .orShould().haveSimpleNameEndingWith("RepositoryImpl")
                .allowEmptyShould(true).check(importedClasses);
    }

    @Test
    void controllerClassesShouldBeNamedXController() {
        classes()
                .that().resideInAPackage("..controller..")
                .should().haveSimpleNameEndingWith("Controller")
                .allowEmptyShould(true).check(importedClasses);
    }

    @Test
    void fieldInjectionNotUseAutowiredAnnotation() {

        noFields()
                .should().beAnnotatedWith(Autowired.class)
                .allowEmptyShould(true).check(importedClasses);
    }
}
