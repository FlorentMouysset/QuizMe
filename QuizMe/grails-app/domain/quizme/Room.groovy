package quizme

import user.Professeur

class Room {
	
	String nom
	Professeur admin
	static hasMany = [sessions:Session]

	
    static constraints = {
    }
}
