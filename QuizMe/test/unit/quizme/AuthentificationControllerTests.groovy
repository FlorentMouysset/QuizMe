package quizme



import org.junit.*
import grails.test.mixin.*

@TestFor(AuthentificationController)
@Mock(Authentification)
class AuthentificationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/authentification/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.authentificationInstanceList.size() == 0
        assert model.authentificationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.authentificationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.authentificationInstance != null
        assert view == '/authentification/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/authentification/show/1'
        assert controller.flash.message != null
        assert Authentification.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/authentification/list'

        populateValidParams(params)
        def authentification = new Authentification(params)

        assert authentification.save() != null

        params.id = authentification.id

        def model = controller.show()

        assert model.authentificationInstance == authentification
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/authentification/list'

        populateValidParams(params)
        def authentification = new Authentification(params)

        assert authentification.save() != null

        params.id = authentification.id

        def model = controller.edit()

        assert model.authentificationInstance == authentification
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/authentification/list'

        response.reset()

        populateValidParams(params)
        def authentification = new Authentification(params)

        assert authentification.save() != null

        // test invalid parameters in update
        params.id = authentification.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/authentification/edit"
        assert model.authentificationInstance != null

        authentification.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/authentification/show/$authentification.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        authentification.clearErrors()

        populateValidParams(params)
        params.id = authentification.id
        params.version = -1
        controller.update()

        assert view == "/authentification/edit"
        assert model.authentificationInstance != null
        assert model.authentificationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/authentification/list'

        response.reset()

        populateValidParams(params)
        def authentification = new Authentification(params)

        assert authentification.save() != null
        assert Authentification.count() == 1

        params.id = authentification.id

        controller.delete()

        assert Authentification.count() == 0
        assert Authentification.get(authentification.id) == null
        assert response.redirectedUrl == '/authentification/list'
    }
}
