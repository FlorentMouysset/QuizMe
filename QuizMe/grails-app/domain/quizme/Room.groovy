package quizme

import user.Etudiant
import user.Professeur

class Room {
	
	String nom
	String mdp
	Professeur admin
	//static hasMany = [sessions:Session, etudiants:Etudiant]
	static hasMany = [ etudiants:Etudiant, sessions:Session]
	
		
    static constraints = {
		nom(blank:false)
		mdp(size: 2..15, blank: false)
    }
	
	boolean estParticipant(String idDemande){
		def found = etudiants.find {
			it.id.toString().equals(idDemande)
		}
		found != null
	}
	
	
	def addParticipant(def etudiant){
		//def etudiantId =  etudiant.getId().toString()
		//println "add particiant " + etudiantId
		//println "AA " + Etudiant.findById(etudiantId)
		//addToSessions(new Session())
		addToEtudiants(etudiant)
		println "VERIF " +  estParticipant(etudiant.id.toString())
	}
}
