package jp.gr.java_conf.a_kura.ant.plusrelax.engine;

public class RelaxEngineFactory {

	public static RelaxEngine create() {
		return new CatRelaxEngine();
	}
	
	public static RelaxEngine create(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return (RelaxEngine)Class.forName(className).newInstance();
	}

	public static RelaxEngine createDebug() {
		return new DebugRelaxEngine();
	}

}
