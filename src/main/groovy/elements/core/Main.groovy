package elements.core

import elements.core.config.Elements
import elements.core.delegates.ElementsDelegate;

class Main {

    static void main(def args) {
        //runContainerRules(new File("/tmp/loader.elements"))
        runContainerRules(this.class.getResource("/loader.elements"))
    }

    static void runContainerRules(URL dsl) {
        Script script = new GroovyShell().parse(new File(dsl.toURI()))
        Elements element = new Elements()
        script.metaClass = createEMC(script.class, { ExpandoMetaClass emc ->

            emc.elements = { Closure cl ->
                cl.delegate = new ElementsDelegate(element)
                cl.resolveStrategy = DELEGATE_FIRST
                cl()
            }
        })
        script.run()
        element.execute()
    }

    static ExpandoMetaClass createEMC(Class clazz, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
        cl(emc)
        emc.initialize()
        return emc
    }
}