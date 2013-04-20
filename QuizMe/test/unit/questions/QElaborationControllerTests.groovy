package questions



import org.junit.*
import grails.test.mixin.*

@TestFor(QElaborationController)
@Mock(QElaboration)
class QElaborationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/QElaboration/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.QElaborationInstanceList.size() == 0
        assert model.QElaborationInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.QElaborationInstance != null
    }

    void testSave() {
        controller.save()

        assert model.QElaborationInstance != null
        assert view == '/QElaboration/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/QElaboration/show/1'
        assert controller.flash.message != null
        assert QElaboration.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/QElaboration/list'

        populateValidParams(params)
        def QElaboration = new QElaboration(params)

        assert QElaboration.save() != null

        params.id = QElaboration.id

        def model = controller.show()

        assert model.QElaborationInstance == QElaboration
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/QElaboration/list'

        populateValidParams(params)
        def QElaboration = new QElaboration(params)

        assert QElaboration.save() != null

        params.id = QElaboration.id

        def model = controller.edit()

        assert model.QElaborationInstance == QElaboration
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/QElaboration/list'

        response.reset()

        populateValidParams(params)
        def QElaboration = new QElaboration(params)

        assert QElaboration.save() != null

        // test invalid parameters in update
        params.id = QElaboration.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/QElaboration/edit"
        assert model.QElaborationInstance != null

        QElaboration.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/QElaboration/show/$QElaboration.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        QElaboration.clearErrors()

        populateValidParams(params)
        params.id = QElaboration.id
        params.version = -1
        controller.update()

        assert view == "/QElaboration/edit"
        assert model.QElaborationInstance != null
        assert model.QElaborationInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/QElaboration/list'

        response.reset()

        populateValidParams(params)
        def QElaboration = new QElaboration(params)

        assert QElaboration.save() != null
        assert QElaboration.count() == 1

        params.id = QElaboration.id

        controller.delete()

        assert QElaboration.count() == 0
        assert QElaboration.get(QElaboration.id) == null
        assert response.redirectedUrl == '/QElaboration/list'
    }
}
