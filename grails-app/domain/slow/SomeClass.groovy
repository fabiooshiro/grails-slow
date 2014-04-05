package slow

class SomeClass {

	String name

    static constraints = {
    }

    static namedQueries = {
    	workaround {
           like 'name', '%'
		}
    }
}
