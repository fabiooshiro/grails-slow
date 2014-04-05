import slow.SomeClass;

class BootStrap {

    def init = { servletContext ->
    	5000.times {
    		new SomeClass(name: "name ${it}").save(failOnError: true)
    	}
    }

    def destroy = {
    }

}
