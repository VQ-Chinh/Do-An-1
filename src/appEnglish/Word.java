package appEnglish;

public class Word {
	private String Name;
	private String Spell;
	private String Explan;
	private String ExplanVN;
	private String Example;
	private String ExampleVN;
	private String Lesson;
	
	

	public String getSpell() {
		return Spell;
	}

	public void setSpell(String spell) {
		Spell = spell;
	}

	public String getExplanVN() {
		return ExplanVN;
	}

	public void setExplanVN(String explanVN) {
		ExplanVN = explanVN;
	}

	public String getExampleVN() {
		return ExampleVN;
	}

	public void setExampleVN(String exampleVN) {
		ExampleVN = exampleVN;
	}

	public String getLesson() {
		return Lesson;
	}

	public void setLesson(String lesson) {
		Lesson = lesson;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getExplan() {
		return Explan;
	}

	public void setExplan(String explan) {
		Explan = explan;
	}

	public String getExample() {
		return Example;
	}

	public void setExample(String example) {
		Example = example;
	}

	
	
	private Word(Builder buil){
		Name = buil.Name;
		Spell = buil.Spell;
		Explan = buil.Explan;
		ExplanVN = buil.ExplanVN;
		Example = buil.Example;
		Example = buil.ExampleVN;
		Lesson = buil.Lesson;
	}
	
	public static class Builder{
		
		private String Name;
		private String Spell;
		private String Explan;
		private String ExplanVN;
		private String Example;
		private String ExampleVN;
		private String Lesson;
		
		public void setName(String name) {
			Name = name;
		}
		public void setExplan(String explan) {
			Explan = explan;
		}
		public void setExample(String example) {
			Example = example;
		}
		
		public void setSpell(String spell) {
			Spell = spell;
		}
		public void setExplanVN(String explanVN) {
			ExplanVN = explanVN;
		}
		public void setExampleVN(String exampleVN) {
			ExampleVN = exampleVN;
		}
		public void setLesson(String lesson) {
			Lesson = lesson;
		}
		
		
		
	}

	public Word(String name, String spell, String explan, String explanVN, String example, String exampleVN,
			String lesson) {
		super();
		Name = name;
		Spell = spell;
		Explan = explan;
		ExplanVN = explanVN;
		Example = example;
		ExampleVN = exampleVN;
		Lesson = lesson;
	}
	
	
}
