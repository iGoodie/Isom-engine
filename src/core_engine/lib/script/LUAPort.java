package lib.script;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LUAPort {
	static Globals _G = JsePlatform.standardGlobals();
	
	/*public static void main(String[] args) throws ScriptException, IOException {
		_G.get("dofile").call("data/scripts/functions.lua");
		System.out.println(_G.get("square").call(LuaValue.valueOf(5)));
		
		System.out.println(_G.STDIN.read());
	}*/
}
