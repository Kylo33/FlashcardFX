module com.kyloapps {
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires java.prefs;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires java.desktop;
    requires com.tobiasdiez.easybind;
    requires com.google.common;
    requires dirtyfx;
    requires atlantafx.base;
    exports com.kyloapps;
    exports com.kyloapps.domain;
    exports com.kyloapps.deckeditor;
    exports com.kyloapps.deckeditor.cardeditor;
    exports com.kyloapps.utils;
    exports com.kyloapps.practice;
    exports com.kyloapps.deckeditor.cardeditor.forms;
    exports com.kyloapps.deckeditor.cardeditor.forms.multiplechoice;
    exports com.kyloapps.deckeditor.cardeditor.forms.classic;
    exports com.kyloapps.deckeditor.cardeditor.forms.table;
}
