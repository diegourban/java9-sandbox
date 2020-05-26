package urban.sandbox.java9.jep213;

public interface InterfaceWithPrivateMethods {

    public abstract void publicAbstractMethod();

    public default void publicDefaultMethod() {
        System.out.println("public default method");
        System.out.println("Calling method: " + instancePrivateMethod());
    }

    public static void publicStaticMethod() {
        System.out.println("public static method");
        System.out.println("Calling method: " + staticPrivateMethod());
    }

    private static String staticPrivateMethod() {
        return "static private";
    }

    private String instancePrivateMethod() {
        return "instance private";
    }

}
