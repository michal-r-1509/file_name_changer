package user_interface;

class HelpText{

    @Override
    public String toString() {
        return "do not use forbidden signs as / \\ : \" ? * | < > in filenames\n" +
                "use / to put current filename (without ignored text)\n" +
                "use * to put current date\n" +
                "use ? to put text from file\n" +
                "your new files will be in chosen directory in /output";
    }
}