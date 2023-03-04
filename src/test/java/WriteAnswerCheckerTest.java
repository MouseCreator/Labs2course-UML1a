import static org.junit.jupiter.api.Assertions.*;

import com.example.lab23a.model.WriteAnswerChecker;
import org.junit.jupiter.api.Test;

class WriteAnswerCheckerTest {
	
	@Test
	void testChecker() {
		WriteAnswerChecker checker = new WriteAnswerChecker();
		checker.setTerm("Answer");
		assertTrue(checker.checkAnswer("Answer"));
		assertTrue(checker.checkAnswer("answer"));
		assertTrue(checker.checkAnswer(" ansWer "));
		assertTrue(checker.checkAnswer(" ANSWER"));
		
		assertFalse(checker.checkAnswer("ANSW ER"));
		assertFalse(checker.checkAnswer("Anser"));
		assertFalse(checker.checkAnswer("Ans"));
		assertFalse(checker.checkAnswer("wer"));
		assertFalse(checker.checkAnswer("~Anwer;'"));
	}

}
