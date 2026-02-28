package core;

import interfases.WritableToJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ticket implements WritableToJson {
    static private long currentId = 1;
    private long id; //+Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; //Значение поля должно быть больше 0
    private String comment; //Поле не может быть null
    private boolean refundable;
    private TicketType type; //Поле может быть null
    private Event event; //Поле может быть null
    public Ticket(){
        id = currentId++;
        creationDate = new Date();
    }

    public void setId(long ticketId) {
        this.id = ticketId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCreationDate(Date date) {this.creationDate = date;}

    public void setCoordinates(Coordinates coordinates){
        this.coordinates = coordinates;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static long getCurrentId() {
        return currentId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public double getPrice() {
        return price;
    }

    public String getComment() {
        return comment;
    }

    public boolean isRefundable() {   // для boolean используется is
        return refundable;
    }

    public TicketType getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }


    @Override
    public String toString(){
        String result = "id: "+id+"\n"+
                "name: "+name+"\n"+
                "coordinates: "+coordinates.toString()+"\n"+
                "creation date: "+creationDate.toString()+"\n"+
                "price: "+price+"\n"+
                "comment: "+comment+"\n"+
                "refundable: "+refundable+"\n"+
                "ticket type: "+type.toString()+"\n"+
                "event: "+event.toString();
        return result;
    }


    public void resetParameters(){
        Scanner scanner = new Scanner(System.in);

        // ===== name =====
        while (true) {
            System.out.print("Введите имя: ");
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                setName(input.trim());
                break;
            }
            System.out.println("Ошибка: имя не может быть пустым.");
        }

        // X
        while (true) {
            System.out.print("Введите X (<= 851): ");
            try {
                Double x = Double.parseDouble(scanner.nextLine().trim());
                if (x <= 851) {
                    coordinates.setX(x);
                    break;
                } else {
                    System.out.println("Ошибка: X должен быть <= 851.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }

        // Y
        while (true) {
            System.out.print("Введите Y (<= 621): ");
            try {
                double y = Double.parseDouble(scanner.nextLine().trim());
                if (y <= 621) {
                    coordinates.setY(y);
                    break;
                } else {
                    System.out.println("Ошибка: Y должен быть <= 621.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }

        setCoordinates(coordinates);

        // ===== price =====
        while (true) {
            System.out.print("Введите цену (> 0): ");
            try {
                double price = Double.parseDouble(scanner.nextLine().trim());
                if (price > 0) {
                    setPrice(price);
                    break;
                } else {
                    System.out.println("Ошибка: цена должна быть > 0.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }

        // ===== comment =====
        while (true) {
            System.out.print("Введите комментарий: ");
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                setComment(input.trim());
                break;
            }
            System.out.println("Ошибка: комментарий не может быть пустым.");
        }

        // ===== refundable =====
        while (true) {
            System.out.print("Возвратный? (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                setRefundable(Boolean.parseBoolean(input));
                break;
            }
            System.out.println("Ошибка: введите true или false.");
        }

        // ===== type (nullable) =====
        while (true) {
            System.out.print("Тип билета (USUAL, BUDGETARY, CHEAP) или пусто: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                setType(null);
                break;
            }
            try {
                setType(TicketType.valueOf(input.toUpperCase()));
                break;
            } catch (Exception e) {
                System.out.println("Ошибка: неверный тип.");
            }
        }

        // ===== event (nullable) =====
        System.out.print("Создать событие? (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("yes")) {
            Event event = new Event();

            // name
            while (true) {
                System.out.print("Имя события: ");
                String input = scanner.nextLine();
                if (input != null && !input.trim().isEmpty()) {
                    event.setName(input.trim());
                    break;
                }
                System.out.println("Ошибка: имя не может быть пустым.");
            }

            // ticketsCount
            while (true) {
                System.out.print("Количество билетов (> 0): ");
                try {
                    long count = Long.parseLong(scanner.nextLine().trim());
                    if (count > 0) {
                        event.setTicketsCount(count);
                        break;
                    }
                    System.out.println("Ошибка: должно быть > 0.");
                } catch (Exception e) {
                    System.out.println("Ошибка: введите число.");
                }
            }

            // eventType
            while (true) {
                System.out.print("Тип события (E_SPORTS, FOOTBALL, BASKETBALL, OPERA, EXPOSITION): ");
                try {
                    EventType eventType = EventType.valueOf(scanner.nextLine().trim().toUpperCase());
                    event.setEventType(eventType);
                    break;
                } catch (Exception e) {
                    System.out.println("Ошибка: неверный тип.");
                }
            }

            setEvent(event);
        } else {
            setEvent(null);
        }
        creationDate = new Date();
    }


    private static String getString(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return null;

        start += pattern.length();

        while (json.charAt(start) == ' ' || json.charAt(start) == '\"') start++;

        int end = start;
        while (json.charAt(end) != '\"') end++;

        return json.substring(start, end);
    }

    private static double getDouble(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return 0;

        start += pattern.length();

        while (json.charAt(start) == ' ') start++;

        int end = start;
        while (end < json.length() && "0123456789.".indexOf(json.charAt(end)) != -1){
            end++;
        }

        return Double.parseDouble(json.substring(start, end));
    }

    private static long getLong(String json, String key){
        String pattern = "\"" + key + "\":";
        int start = json.indexOf(pattern);
        if (start == -1) return 0;

        start += pattern.length();

        while (json.charAt(start) == ' ') start++;

        int end = start;
        while (end < json.length() && "0123456789".indexOf(json.charAt(end)) != -1){
            end++;
        }

        return Long.parseLong(json.substring(start, end));
    }

    private static boolean getBoolean(String json, String key){
        String value = getString(json, key);
        return Boolean.parseBoolean(value);
    }

    public static <T> T fromJson(String json, int index, Class<?> objectType) throws Exception {
        Pattern pattern = Pattern.compile("\"(.*?)\":");
        Matcher matcher = pattern.matcher(json);
        T result = (T) objectType.getDeclaredConstructor().newInstance();
        while (matcher.find(index)) {
            String filedName = matcher.group(1);
            index = matcher.end();
            Field field;
            try {
                field = objectType.getDeclaredField(filedName);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object value;
            value = switch (fieldType.getName()){
                case "java.lang.String" -> getString(json, filedName);
                case "double", "java.lang.Double" -> getDouble(json, filedName);
                case "long", "java.lang.Long" -> getLong(json, filedName);
                case "boolean" -> getBoolean(json, filedName);
                case "core.EventType", "core.TicketType" -> Enum.valueOf((Class<Enum>) fieldType, getString(json, filedName));
                case "java.util.Date" -> {
                    try{
                        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        yield formatter.parse(getString(json, "creationDate"));
                    } catch (Exception exception){
                        yield new Date();
                    }
                }
                case "core.Coordinates", "core.Event" -> {
                    Pattern jsonPattern = Pattern.compile("\\{.*?\\}", Pattern.DOTALL);
                    Matcher jsonMatcher = jsonPattern.matcher(json);
                    jsonMatcher.find(index);
                    index = jsonMatcher.end();
                    yield fromJson(jsonMatcher.group(0), 0, fieldType);
                }
                default -> throw new IllegalStateException("Unexpected value: " + fieldType.getName());
            };
            field.set(result, value);
        }
        return result;
    }

    public static String toJson(Object object, int deep){
        String result = "";
        if (WritableToJson.class.isAssignableFrom(object.getClass())){
            result += "{\n\t";
            for (int i = 0; i < deep; i++) {
                result += "\t";
            }
            Field[] fields = object.getClass().getDeclaredFields();
            Iterator<Field> fieldIterator = Arrays.stream(fields).iterator();
            while (fieldIterator.hasNext()){
                Field field = fieldIterator.next();
                field.setAccessible(true);
                try {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    result += field.getName()+": "+toJson(field.get(object), deep + 1);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            result += "},\n\t";

        }
        else {
            result += object+",\n";
            for (int i = 0; i < deep; i++) {
                result += "\t";
            }
        }

        return result;
    }

}

