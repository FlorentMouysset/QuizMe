package user

class Professeur extends User {

    static constraints = {
    }
	
	static boolean estProfesseur(def user){
		user.class.equals( Professeur.class )
	}
	
	
}