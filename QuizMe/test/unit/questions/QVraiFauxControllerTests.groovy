package questions



import org.junit.*
import grails.test.mixin.*

@TestFor(QVraiFauxController)
@Mock(QVraiFaux)
class QVraiFauxControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/QVraiFaux/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.QVraiFauxInstanceList.size() == 0
        assert model.QVraiFauxInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.QVraiFauxInstance != null
    }

    void testSave() {
        controller.save()

        assert model.QVraiFauxInstance != null
        assert view == '/QVraiFaux/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/QVraiFaux/show/1'
        assert controller.flash.message != null
        assert QVraiFaux.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/QVraiFaux/list'

        populateValidParams(params)
        def QVraiFaux = new QVraiFaux(params)

        assert QVraiFaux.save() != null

        params.id = QVraiFaux.id

        def model = controller.show()

        assert model.QVraiFauxInstance == QVraiFaux
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/QVraiFaux/list'

        populateValidParams(params)
        def QVraiFaux = new QVraiFaux(params)

        assert QVraiFaux.save() != null

        params.id = QVraiFaux.id

        def model = controller.edit()

        assert model.QVraiFauxInstance == QVraiFaux
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/QVraiFaux/list'

        response.reset()

        populateValidParams(params)
        def QVraiFaux = new QVraiFaux(params)

        assert QVraiFaux.save() != null

        // test invalid parameters in update
        params.id = QVraiFaux.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/QVraiFaux/edit"
        assert model.QVraiFauxInstance != null

        QVraiFaux.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/QVraiFaux/show/$QVraiFaux.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        QVraiFaux.clearErrors()

        populateValidParams(params)
        params.id = QVraiFaux.id
        params.version = -1
        controller.update()

        assert view == "/QVraiFaux/edit"
        assert model.QVraiFauxInstance != null
        assert model.QVraiFauxInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/QVraiFaux/list'

        response.reset()

        populateValidParams(params)
        def QVraiFaux = new QVraiFaux(params)

        assert QVraiFaux.save() != null
        assert QVraiFaux.count() == 1

        params.id = QVraiFaux.id

        controller.delete()

        assert QVraiFaux.count() == 0
        assert QVraiFaux.get(QVraiFaux.id) == null
        assert response.redirectedUrl == '/QVraiFaux/list'
    }
}
