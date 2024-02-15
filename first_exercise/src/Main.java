import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Painter Calculator!");
        Scanner scanner = new Scanner(System.in);

        double totalArea = 0;
        double totalCost = 0;

        while (true) {


            System.out.println("Enter the type of shape: Rectangle or Circle ");
            String shapeType = scanner.nextLine();
            double area = 0;

            switch (shapeType.toLowerCase()) {
                case "rectangle":
                    System.out.println("Enter the width (in meters): ");
                    double width = scanner.nextDouble();
                    System.out.println("Enter the height (in meters): ");
                    double height = scanner.nextDouble();
                    area = width * height;
                    break;
                case "circle":
                    System.out.println("Enter the radius (in meters): ");
                    double radius = scanner.nextDouble();
                    area = Math.PI * Math.pow(radius, 2);
                    break;
                default:
                    System.out.println("Invalid shape type entered. Press enter to continue");
                    scanner.nextLine();
                    continue;
            }
            totalArea += area;

            System.out.println("Do you want to enter the cost for the paint? (yes / anything else for no)");
            scanner.nextLine();
            String answer = scanner.nextLine();
            double cost = 0;

            if (answer.equalsIgnoreCase("yes")) {
                while (true) {
                    System.out.println("Enter the cost per square meter: ");
                    if (scanner.hasNextDouble()) {
                        cost = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        break;
                    } else {
                        System.out.println("Invalid cost. Please enter a valid number.");
                        scanner.next(); // Consume the invalid input
                    }
                }
            }else {

                System.out.println("Select a cost range per square meter:");
                System.out.println("1. £1.50");
                System.out.println("2. £2.50");
                System.out.println("3. £3.50");
                int costChoice = scanner.nextInt();
                switch (costChoice) {
                    case 1:
                        cost = 1.50;
                        break;
                    case 2:
                        cost = 2.50;
                        break;
                    case 3:
                        cost = 3.50;
                        break;
                    default:
                        System.out.println("Invalid choice. Using £2.50.");
                        cost = 2.50;
                }
            }
            double currentCost = area * cost;
            totalCost += currentCost;

            System.out.printf("Amount of paint needed for this shape: %.2f square meters.\n", area);
            System.out.printf("Cost for this shape: £%.2f\n", currentCost);
            System.out.println("Do you wish to calculate for another shape? (yes / anything else for no)");
            scanner.nextLine();
            String continueAnswer = scanner.nextLine();
            if (!continueAnswer.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.printf("Total amount of paint needed: %.2f square meters.\n", totalArea);
        System.out.printf("Total cost for all paint: £%.2f\n", totalCost);
        scanner.close();
    }
}
