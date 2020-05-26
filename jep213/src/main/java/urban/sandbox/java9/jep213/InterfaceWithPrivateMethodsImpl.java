package urban.sandbox.java9.jep213;

public class InterfaceWithPrivateMethodsImpl implements InterfaceWithPrivateMethods {

    @Override
    public void publicAbstractMethod() {
        System.out.println("public abstract method impl");
    }

    public static void main(String[] args) {
        InterfaceWithPrivateMethods.publicStaticMethod();

        final InterfaceWithPrivateMethods interfaceWithPrivateMethods = new InterfaceWithPrivateMethodsImpl();
        interfaceWithPrivateMethods.publicAbstractMethod();
        interfaceWithPrivateMethods.publicDefaultMethod();
    }

}
