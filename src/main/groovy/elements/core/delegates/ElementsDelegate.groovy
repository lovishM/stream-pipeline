package elements.core.delegates

import elements.core.config.Elements

/**
 * Created by lovish on 30/7/16.
 */
class ElementsDelegate {
    private Elements element

    ElementsDelegate(Elements element) {
        this.element = element
    }

    void name(String element) {
        this.element.name = element
    }

    void after(Map<String, Closure> executable) {
        executable.each {k,cl->
            println "Executing after [$k]"
            cl()
        }
    }

    void apply(Map map) {
        map.each {k,v->
            println "Applying [$k] : [$v]"
        }
    }

}
