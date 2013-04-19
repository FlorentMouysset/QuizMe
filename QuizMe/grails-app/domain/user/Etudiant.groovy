package user

class Etudiant extends User {

	String numEtudiant

	
    static constraints = {
		//TODO Grosse erreur sur les contraintes : size : 8..8 peut etre?
		//numEtudiant size: 8..8, blank: false, unique: true
    }


	
	/*String toString() {
		"Etudiant [numEtudiant=" + numEtudiant + "]"
	}*/	
	
	static boolean estEtudiant(def user){
		user.class.equals( Etudiant.class )
	}
	
	
}
