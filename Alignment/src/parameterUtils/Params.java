package parameterUtils;

import java.util.HashMap;

import enums.AlignmentFormat;
import enums.AlignmentType;

public class Params {
		private float go;
		private float ge;
		private String dpmatrices;
		private boolean check;
		private String pairs;
		private String seqlib;
		private String m;
		private AlignmentType mode;
		private boolean nw;
		private AlignmentFormat format;
		
		public Params(HashMap<String, String> params){

			try {
				if (params.get("nw") != null) { nw = true; } else { nw = false; }
				
				if (params.get("go") != null) { 
					if (nw) { go = 0; }
					else { go = Float.parseFloat(params.get("go")); }
				} else { 
					if (nw) { go = 0; } 
					else { go = -12; } 
				}
				
				if (params.get("ge") != null) { ge = Float.parseFloat(params.get("ge")); } else { ge = -1; }
				
				if (params.get("dpmatrices") != null) dpmatrices = params.get("dpmatrices");
				
				if (params.get("check") != null) { check = true; } else { check = false; }
				
				if (params.get("pairs") != null){
					pairs = params.get("pairs");
				} else {
					throw new ParamException();
				}
				
				if (params.get("seqlib") != null){
					seqlib = params.get("seqlib");
				} else {
					throw new ParamException();
				}
				
				if (params.get("m") != null){
					m = params.get("m");
				} else {
					throw new ParamException();
				}
				
				if (params.get("mode") == null) throw new ParamException();
				for (AlignmentType et : AlignmentType.values()){
					if (et.name().equalsIgnoreCase(params.get("mode"))) this.mode = et; 
				}
				if (mode == null) throw new ParamException();
				
				if (params.get("format") == null) throw new ParamException();
				for (AlignmentFormat af : AlignmentFormat.values()){
					if (af.name().equalsIgnoreCase(params.get("format"))) this.format = af; 
				}
				if (format == null) throw new ParamException();
			} catch (ParamException pe){
				ParamException.printHelp();
			}
		}
		
		public static HashMap<String, String> getParams(String[] args){
			HashMap<String, String> params = new HashMap<String, String>();
			
			for (int i = 0; i < args.length; i++){
				if(args[i].charAt(0) == '-'){
					if(args[i].charAt(1) == 'm') {
						params.put("m", args[i+1]);
					} else if (args[i].charAt(1) == '-'){
						String flag = args[i].substring(2);
						String par = ""; 
						if(i+1 < args.length) {
							if (args[i+1].charAt(0) != '-' || (int) args[i+1].charAt(0) < 57) {
								par = args[i+1];
							}
						}
						
						switch (flag) {
							case "go": params.put("go", par); break;
							case "ge": params.put("ge", par); break;
							case "dpmatrices": params.put("dpmatrices", par); break;
							case "check": params.put("check", "true"); break;
							case "pairs": params.put("pairs",par); break;
							case "seqlib": params.put("seqlib", par); break;
							case "mode": params.put("mode", par); break;
							case "nw": params.put("nw", "true"); break;
							case "format": params.put("format", par); break;
						}
					}
				}
			}
			
			return params;
		}

		public float getGo() {
			return go;
		}

		public float getGe() {
			return ge;
		}

		public String getDpmatrices() {
			return dpmatrices;
		}
		
		public boolean isCheck() {
			return check;
		}

		public String getPairs() {
			return pairs;
		}

		public String getSeqlib() {
			return seqlib;
		}

		public String getM() {
			return m;
		}

		public AlignmentType getMode() {
			return mode;
		}

		public boolean isNw() {
			return nw;
		}

		public AlignmentFormat getFormat() {
			return format;
		}

		@Override
		public String toString() {
			return "Params [go=" + go + ", ge=" + ge + ", dpmatrices=" + dpmatrices + ", check=" + check + ", pairs="
					+ pairs + ", seqlib=" + seqlib + ", m=" + m + ", mode=" + mode + ", nw=" + nw + ", format=" + format
					+ "]";
		}
}
