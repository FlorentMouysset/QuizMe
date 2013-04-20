package questions



import org.junit.*
import grails.test.mixin.*

@TestFor(QLibreController)
@Mock(QLibre)
class QLibreControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/QLibre/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.QLibreInstanceList.size() == 0
        assert model.QLibreInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.QLibreInstance != null
    }

    void testSave() {
        controller.save()

        assert model.QLibreInstance != null
        assert view == '/QLibre/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/QLibre/show/1'
        assert controller.flash.message != null
        assert QLibre.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/QLibre/list'

        populateValidParams(params)
        def QLibre = new QLibre(params)

        assert QLibre.save() != null

        params.id = QLibre.id

        def model = controller.show()

        assert model.QLibreInstance == QLibre
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/QLibre/list'

        populateValidParams(params)
        def QLibre = new QLibre(params)

        assert QLibre.save() != null

        params.id = QLibre.id

        def model = controller.edit()

        assert model.QLibreInstance == QLibre
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/QLibre/list'

        response.reset()

        populateValidParams(params)
        def QLibre = new QLibre(params)

        assert QLibre.save() != null

        // test invalid parameters in update
        params.id = QLibre.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/QLibre/edit"
        assert model.QLibreInstance != null

        QLibre.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/QLibre/show/$QLibre.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        QLibre.clearErrors()

        populateValidParams(params)
        params.id = QLibre.id
        params.version = -1
        controller.update()

        assert view == "/QLibre/edit"
        assert model.QLibreInstance != null
        assert model.QLibreInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/QLibre/list'

        response.reset()

        populateValidParams(params)
        def QLibre = new QLibre(params)

        assert QLibre.save() != null
        assert QLibre.count() == 1

        params.id = QLibre.id

        controller.delete()

        assert QLibre.count() == 0
        assert QLibre.get(QLibre.id) == null
        assert response.redirectedUrl == '/QLibre/list'
    }
}
