package questions



import org.junit.*
import grails.test.mixin.*

@TestFor(QMultiChoixController)
@Mock(QMultiChoix)
class QMultiChoixControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/QMultiChoix/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.QMultiChoixInstanceList.size() == 0
        assert model.QMultiChoixInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.QMultiChoixInstance != null
    }

    void testSave() {
        controller.save()

        assert model.QMultiChoixInstance != null
        assert view == '/QMultiChoix/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/QMultiChoix/show/1'
        assert controller.flash.message != null
        assert QMultiChoix.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/QMultiChoix/list'

        populateValidParams(params)
        def QMultiChoix = new QMultiChoix(params)

        assert QMultiChoix.save() != null

        params.id = QMultiChoix.id

        def model = controller.show()

        assert model.QMultiChoixInstance == QMultiChoix
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/QMultiChoix/list'

        populateValidParams(params)
        def QMultiChoix = new QMultiChoix(params)

        assert QMultiChoix.save() != null

        params.id = QMultiChoix.id

        def model = controller.edit()

        assert model.QMultiChoixInstance == QMultiChoix
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/QMultiChoix/list'

        response.reset()

        populateValidParams(params)
        def QMultiChoix = new QMultiChoix(params)

        assert QMultiChoix.save() != null

        // test invalid parameters in update
        params.id = QMultiChoix.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/QMultiChoix/edit"
        assert model.QMultiChoixInstance != null

        QMultiChoix.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/QMultiChoix/show/$QMultiChoix.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        QMultiChoix.clearErrors()

        populateValidParams(params)
        params.id = QMultiChoix.id
        params.version = -1
        controller.update()

        assert view == "/QMultiChoix/edit"
        assert model.QMultiChoixInstance != null
        assert model.QMultiChoixInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/QMultiChoix/list'

        response.reset()

        populateValidParams(params)
        def QMultiChoix = new QMultiChoix(params)

        assert QMultiChoix.save() != null
        assert QMultiChoix.count() == 1

        params.id = QMultiChoix.id

        controller.delete()

        assert QMultiChoix.count() == 0
        assert QMultiChoix.get(QMultiChoix.id) == null
        assert response.redirectedUrl == '/QMultiChoix/list'
    }
}
