package eg.edu.alexu.csd.oop.Circus.Shapes;

public class ShapeState {
    private double vX;
    private double vY;
    private double aY;
    private double a;
    private double b;
    public ShapeState(double vX, double vY, double dragCoeffX, double dragCoeffY, double aY){
        this.vX = vX;
        this.vY = vY;
        this.aY = aY;
        a = dragCoeffX;
        b = dragCoeffY;
    }
    public double getVelocityX(){
        return vX;
    }
    public double getVelocityY(){
        return vY;
    }
    public double getAcceleration(){
        return aY;
    }
    public void setParameters( double dragCoeffX, double dragCoeffY, double aY){
        this.a = dragCoeffX;
        this.b = dragCoeffY;
        this.aY = aY;
    }
    public void move(Shape s){
        int x = s.getX();
        int y = s.getY();
        int newX = (int) Math.round(x + vX);
        int newY = (int) Math.round(y + vY);
        if(newX+s.getWidth()/2 > s.getScreenWidth() || newX+s.getWidth()/2 < 0){
            reflect(newX+s.getWidth()/2 > s.getScreenWidth());
            newX =  (int)Math.round(x + vX);
            newY = (int)Math.round(y + vY);
        }
        s.setX(newX);
        s.setY(newY);
        update();
    }
    public void update(){
        vX = (vX * Math.exp(-a));
        vY = (aY - (aY - b*vY) * Math.exp(-b))/b;

    }
    public void reflect(boolean right){
        if(vX > 0 && right) {
            vX = (-vX * 0.5);
        }else if( vX < 0 && !right){
            vX = (-vX * 0.5);
        }
    }
    public ShapeState clone(){
        return new ShapeState(vX, vY, a, b ,aY);

    }
}
