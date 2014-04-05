package slow



import org.junit.*
import grails.test.mixin.*

@TestFor(SomeClassController)
@Mock(SomeClass)
class SomeClassControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/someClass/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.someClassInstanceList.size() == 0
        assert model.someClassInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.someClassInstance != null
    }

    void testSave() {
        controller.save()

        assert model.someClassInstance != null
        assert view == '/someClass/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/someClass/show/1'
        assert controller.flash.message != null
        assert SomeClass.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/someClass/list'

        populateValidParams(params)
        def someClass = new SomeClass(params)

        assert someClass.save() != null

        params.id = someClass.id

        def model = controller.show()

        assert model.someClassInstance == someClass
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/someClass/list'

        populateValidParams(params)
        def someClass = new SomeClass(params)

        assert someClass.save() != null

        params.id = someClass.id

        def model = controller.edit()

        assert model.someClassInstance == someClass
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/someClass/list'

        response.reset()

        populateValidParams(params)
        def someClass = new SomeClass(params)

        assert someClass.save() != null

        // test invalid parameters in update
        params.id = someClass.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/someClass/edit"
        assert model.someClassInstance != null

        someClass.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/someClass/show/$someClass.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        someClass.clearErrors()

        populateValidParams(params)
        params.id = someClass.id
        params.version = -1
        controller.update()

        assert view == "/someClass/edit"
        assert model.someClassInstance != null
        assert model.someClassInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/someClass/list'

        response.reset()

        populateValidParams(params)
        def someClass = new SomeClass(params)

        assert someClass.save() != null
        assert SomeClass.count() == 1

        params.id = someClass.id

        controller.delete()

        assert SomeClass.count() == 0
        assert SomeClass.get(someClass.id) == null
        assert response.redirectedUrl == '/someClass/list'
    }
}
