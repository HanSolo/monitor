module eu.hansolo.fx.monitor {
    // Java
    requires java.base;

    // Java-FX
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;

    exports eu.hansolo.fx.monitor;
    exports eu.hansolo.fx.monitor.tools;
}