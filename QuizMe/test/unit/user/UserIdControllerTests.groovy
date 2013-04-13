package user



import org.junit.*
import grails.test.mixin.*

@TestFor(UserIdController)
@Mock(UserId)
class UserIdControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userId/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userIdInstanceList.size() == 0
        assert model.userIdInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userIdInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userIdInstance != null
        assert view == '/userId/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userId/show/1'
        assert controller.flash.message != null
        assert UserId.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userId/list'

        populateValidParams(params)
        def userId = new UserId(params)

        assert userId.save() != null

        params.id = userId.id

        def model = controller.show()

        assert model.userIdInstance == userId
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userId/list'

        populateValidParams(params)
        def userId = new UserId(params)

        assert userId.save() != null

        params.id = userId.id

        def model = controller.edit()

        assert model.userIdInstance == userId
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userId/list'

        response.reset()

        populateValidParams(params)
        def userId = new UserId(params)

        assert userId.save() != null

        // test invalid parameters in update
        params.id = userId.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userId/edit"
        assert model.userIdInstance != null

        userId.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userId/show/$userId.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userId.clearErrors()

        populateValidParams(params)
        params.id = userId.id
        params.version = -1
        controller.update()

        assert view == "/userId/edit"
        assert model.userIdInstance != null
        assert model.userIdInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userId/list'

        response.reset()

        populateValidParams(params)
        def userId = new UserId(params)

        assert userId.save() != null
        assert UserId.count() == 1

        params.id = userId.id

        controller.delete()

        assert UserId.count() == 0
        assert UserId.get(userId.id) == null
        assert response.redirectedUrl == '/userId/list'
    }
}
