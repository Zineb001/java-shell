// Generated from uk\ac\u005Cucl\shell\ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShellGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, UNQUOTED=5, SINGLEQUOTED=6, BACKOQUTED=7, 
		DOUBLE_QUOTE=8, WSS=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "UNQUOTED", "SINGLEQUOTED", "BACKOQUTED", 
		"DOUBLE_QUOTE", "WSS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "';'", "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "UNQUOTED", "SINGLEQUOTED", "BACKOQUTED", 
		"DOUBLE_QUOTE", "WSS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ShellGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13D\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\6\6\6\37\n\6\r\6\16\6 \3\7\3\7\7\7%\n\7\f\7"+
		"\16\7(\13\7\3\7\3\7\3\b\3\b\7\b.\n\b\f\b\16\b\61\13\b\3\b\3\b\3\t\3\t"+
		"\7\t\67\n\t\f\t\16\t:\13\t\3\t\3\t\3\n\6\n?\n\n\r\n\16\n@\3\n\3\n\2\2"+
		"\13\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\3\2\7\t\2\13\13\"\"$$))="+
		">@@~~\4\2))bb\3\2bb\4\2\f\f$$\4\2\13\13\"\"\2H\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\3\25\3\2\2\2\5\27\3\2\2\2\7\31\3\2\2\2\t\33\3\2\2"+
		"\2\13\36\3\2\2\2\r\"\3\2\2\2\17+\3\2\2\2\21\64\3\2\2\2\23>\3\2\2\2\25"+
		"\26\7~\2\2\26\4\3\2\2\2\27\30\7=\2\2\30\6\3\2\2\2\31\32\7>\2\2\32\b\3"+
		"\2\2\2\33\34\7@\2\2\34\n\3\2\2\2\35\37\n\2\2\2\36\35\3\2\2\2\37 \3\2\2"+
		"\2 \36\3\2\2\2 !\3\2\2\2!\f\3\2\2\2\"&\7)\2\2#%\n\3\2\2$#\3\2\2\2%(\3"+
		"\2\2\2&$\3\2\2\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\7)\2\2*\16\3\2\2\2"+
		"+/\7b\2\2,.\n\4\2\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\62"+
		"\3\2\2\2\61/\3\2\2\2\62\63\7b\2\2\63\20\3\2\2\2\648\7$\2\2\65\67\n\5\2"+
		"\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2\2\289\3\2\2\29;\3\2\2\2:8\3\2\2\2"+
		";<\7$\2\2<\22\3\2\2\2=?\t\6\2\2>=\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2"+
		"\2AB\3\2\2\2BC\b\n\2\2C\24\3\2\2\2\b\2 &/8@\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}