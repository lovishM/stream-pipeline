package elements.core.config

/**
 * Created by lovish on 30/7/16.
 */
class Elements {

    String name

    void setName(String name) {
        this.name = name
    }

    void execute() {
        println "Elements is ${name}"
    }
}
