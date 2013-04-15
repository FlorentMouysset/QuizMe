package quizme

class Session {
	
	String nom
	Room room

    static constraints = {
		room(nullable:true)
    }
}
