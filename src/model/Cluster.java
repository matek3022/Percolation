package model;

import java.util.LinkedList;

public class Cluster {
    /**
     * точки кластера
     */
    private LinkedList<Point> points;


    /**
     * список точек для дейкстры
     */
    private LinkedList<Point> deikstraPoints;

    /**
     * кратчайшие пути до других кластеров
     */
    private LinkedList<Road> roads;
    /**
     * этот кластер есть в первой строке
     */
    private boolean isTopCluster = false;
    /**
     * этот кластер есть в последней строке
     */
    private boolean isBottomCluster = false;
    /**
     * этот кластер имеет полный путь с 1 до последней строки
     */
    private boolean isTopAndBottomCluster = false;

    /**
     * для итераций алгоритма дейкстры
     */
    private int currMinLength = Integer.MAX_VALUE;

    /**
     * для итераций алгоритма дейкстры
     */
    private Cluster currPrevCluster = this;

    public Cluster(Table table, LinkedList<Point> points) {
        this.points = points;
        processTopAndBottom(table);
    }

    public Cluster(Table table, Point point) {
        this.points = new LinkedList<>();
        points.add(point);
        processTopAndBottom(table);
    }

    private void processTopAndBottom(Table table) {
        for (Point iterator : points) {
            if (iterator.getCoordY() == 1) {
                isTopCluster = true;
            }
            if (iterator.getCoordY() == table.getM()) {
                isBottomCluster = true;
            }
        }
        isTopAndBottomCluster = isTopCluster && isBottomCluster;
    }

    /**
     * добавляем путь из this кластера до cluster, если он не текущий
     * @param cluster
     */
    public void addRoadToCluster(Cluster cluster) {
        if (roads == null) roads = new LinkedList<>();
        if (this != cluster) {
            roads.add(new Road(this, cluster));
        }
    }

    /**
     * Добавляем пути из this кластера до всех кластеров списка
     * @param clusters
     */
    public void addRoadsToClusters(LinkedList<Cluster> clusters) {
        for (Cluster iterator : clusters) {
            addRoadToCluster(iterator);
        }
    }

    public void processDeikstraIntoTopCluster(Point idealSecondPoint) {
        processDeikstraIntoRoad(idealSecondPoint, null, false);
        int currMin = Integer.MAX_VALUE;
        Point currIdealFirstPoint = null;
        for (Point iter : points) {
            if (iter.getCoordY() == 1 && iter.getDeikstraValue() < currMin) {
                currMin = iter.getDeikstraValue();
                currIdealFirstPoint = iter;
            }
        }
        for (Point iter : points) {
            iter.setDeikstraValue(Integer.MAX_VALUE);
        }
        processDeikstraIntoRoad(currIdealFirstPoint, idealSecondPoint, true);
    }

    public void processDeikstraIntoBottomCluster(Point idealFirstPoint, Table table) {
        processDeikstraIntoRoad(idealFirstPoint, null, false);
        int currMin = Integer.MAX_VALUE;
        Point currIdealSecondPoint = null;
        for (Point iter : points) {
            if (iter.getCoordY() == table.getM() && iter.getDeikstraValue() < currMin) {
                currMin = iter.getDeikstraValue();
                currIdealSecondPoint = iter;
            }
        }
        for (Point iter : points) {
            iter.setDeikstraValue(Integer.MAX_VALUE);
        }
        processDeikstraIntoRoad(idealFirstPoint, currIdealSecondPoint, true);
    }

    public void processDeikstraIntoTopAndBottomCluster(Table table) {
        Point currIdealFirstPoint = null;
        int currMin = Integer.MAX_VALUE;
        for (Point iter : points) {
            if (iter.getCoordY() == 1) {
                processDeikstraIntoRoad(iter, null, false);
                int currCurrMin = Integer.MAX_VALUE;
                for (Point it : points) {
                    if (it.getCoordY() == table.getM() && it.getDeikstraValue() < currCurrMin) {
                        currCurrMin = it.getDeikstraValue();
                    }
                }
                if (currCurrMin < currMin) {
                    currMin = currCurrMin;
                    currIdealFirstPoint = iter;
                }
                for (Point iterr : points) {
                    iterr.setDeikstraValue(Integer.MAX_VALUE);
                }
            }
        }
        processDeikstraIntoBottomCluster(currIdealFirstPoint, table);
    }

    public void processDeikstraIntoRoad(Point idealFirstPoint, Point idealSecondPoint, boolean withColor) {
        idealFirstPoint.setDeikstraValue(1);
        processDeikstra(idealFirstPoint);
        if (withColor) {
            processColorAndDeikstraRoad(idealSecondPoint);
        }
    }

    private void processDeikstra(Point point) {
        Point left = getPoint(point.getCoordX() - 1, point.getCoordY());
        Point right = getPoint(point.getCoordX() + 1, point.getCoordY());
        Point top = getPoint(point.getCoordX(), point.getCoordY() + 1);
        Point bottom = getPoint(point.getCoordX(), point.getCoordY() - 1);
        if (left != null) {
            if (left.getDeikstraValue() > point.getDeikstraValue() + 1) {
                left.setDeikstraValue(point.getDeikstraValue() + 1);
                processDeikstra(left);
            }
        }
        if (right != null) {
            if (right.getDeikstraValue() > point.getDeikstraValue() + 1) {
                right.setDeikstraValue(point.getDeikstraValue() + 1);
                processDeikstra(right);
            }
        }
        if (top != null) {
            if (top.getDeikstraValue() > point.getDeikstraValue() + 1) {
                top.setDeikstraValue(point.getDeikstraValue() + 1);
                processDeikstra(top);
            }
        }
        if (bottom != null) {
            if (bottom.getDeikstraValue() > point.getDeikstraValue() + 1) {
                bottom.setDeikstraValue(point.getDeikstraValue() + 1);
                processDeikstra(bottom);
            }
        }
    }

    private void processColorAndDeikstraRoad(Point idealSecondPoint) {
        deikstraPoints = new LinkedList<>();
        int currDeikstraValue = idealSecondPoint.getDeikstraValue();
        Point currDeikstraPoint = idealSecondPoint;
        deikstraPoints.add(currDeikstraPoint);
        for (int i = 0; i < idealSecondPoint.getDeikstraValue(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (points.get(j).getDeikstraValue() == currDeikstraValue) {
                    int x = points.get(j).getCoordX();
                    int y = points.get(j).getCoordY();
                    int currDeikstraX = currDeikstraPoint.getCoordX();
                    int currDeikstraY = currDeikstraPoint.getCoordY();

                    if (currDeikstraX - 1 == x && currDeikstraY == y) {
                        deikstraPoints.add(points.get(j));
                        currDeikstraValue--;
                        currDeikstraPoint = points.get(j);
                        break;
                    } else if (currDeikstraX + 1 == x && currDeikstraY == y) {
                        deikstraPoints.add(points.get(j));
                        currDeikstraValue--;
                        currDeikstraPoint = points.get(j);
                        break;
                    } else if (currDeikstraX == x && currDeikstraY - 1 == y) {
                        deikstraPoints.add(points.get(j));
                        currDeikstraValue--;
                        currDeikstraPoint = points.get(j);
                        break;
                    } else if (currDeikstraX == x && currDeikstraY + 1 == y) {
                        deikstraPoints.add(points.get(j));
                        currDeikstraValue--;
                        currDeikstraPoint = points.get(j);
                        break;
                    }
                }
            }
        }
        for (Point iter : deikstraPoints) {
            if (iter.getValue() == Point.BLACK_POINT)
                iter.setValue(Point.GREEN_POINT);
        }
    }

    private Point getPoint(int x, int y) {
        Point res = null;
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getCoordX() == x && points.get(i).getCoordY() == y) {
                res = points.get(i);
            }
        }
        return res;
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public LinkedList<Road> getRoads() {
        return roads;
    }

    public boolean isTopCluster() {
        return isTopCluster;
    }

    public boolean isBottomCluster() {
        return isBottomCluster;
    }

    public boolean isTopAndBottomCluster() {
        return isTopAndBottomCluster;
    }

    public int getCurrMinLength() {
        return currMinLength;
    }

    public void setCurrMinLength(int currMinLength) {
        this.currMinLength = currMinLength;
    }

    public Cluster getCurrPrevCluster() {
        return currPrevCluster;
    }

    public void setCurrPrevCluster(Cluster currPrevCluster) {
        this.currPrevCluster = currPrevCluster;
    }

    public LinkedList<Point> getDeikstraPoints() {
        return deikstraPoints;
    }

    public void setDeikstraPoints(LinkedList<Point> deikstraPoints) {
        this.deikstraPoints = deikstraPoints;
    }
}
