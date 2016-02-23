package parameterUtils;

@SuppressWarnings("serial")
public class ParamException extends Exception{
	private static String helpText = "Syntax:\n" +
"java ­jar alignment.jar [­­--go <gapopen>] --[­­ge <gapextend>]\n" +
"\t[--­­dpmatrices <dir>] [­­--check]" +
"\t--pairs <pairfile> ­­seqlib <seqlibfile>\n" + 
"\t-­m <matrixname>\n" +
"\t--­­mode <local|global|freeshift> [­­--nw]\n" +
"\t­­format <scores|ali|html>\n" +
"Options:\n" +
"\t--­­pairs <pairfile> \t\t path to pairs file\n" +
"\t--seqlib <seqlibfile> \t\t path to sequence library file\n" +
"\t-m <matrixname> \t\t path to substitution matrix file\n" +
"\t--go <gapopen> \t\t\t gap open penalty (default -­12)\n" +
"\t--ge <gapextend> \t\t gap extend penalty (default ­-1)\n" + 
"\t--mode <local|global|freeshift>\n" + 
"\t--nw \t\t\t\t use NW/SW algorithms\n" +
"\t--format <scores|ali|html> \t output format\n" +
"\t\tscores: \t scores only\n" +
"\t\tali: \t\t scores + alignment in plaintext\n" +
"\t\thtml: \t\t scores + alignment in html\n" +
"\t--dpmatrices <directory> \t output dynamic programming matrices ­­check calculate checkscores for all\n" +
"\t--check \t\t\t calculate checkscores for all alignments\n" + 
"\t\t\t\t\t and output only incorrect alignments\n";
	
	public ParamException() {}
	
	public static void printHelp(){
		System.out.println(helpText);
	};
}
