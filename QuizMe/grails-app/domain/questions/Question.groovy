package questions


class Question {
	String enonce

    static constraints = {
		enonce(blank:false)
    }
	
	static boolean isMultiChoix(Long id){
		def q = Question.findById(id)
		q.class.equals(QMultiChoix.class) 
	}
	
	
	static boolean isTrueFalse(Long id){
		def q = Question.findById(id)
		q.class.equals(QVraiFaux.class)
	}
	
	static boolean isQElaboration(Long id){
		def q = Question.findById(id)
		q.class.equals(QElaboration.class)
	}

	static boolean isQLibre(Long id){
		def q = Question.findById(id)
		q.class.equals(QLibre.class)
	}
}

