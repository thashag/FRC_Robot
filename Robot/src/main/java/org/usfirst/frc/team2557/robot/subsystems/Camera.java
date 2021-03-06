package org.usfirst.frc.team2557.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import org.usfirst.frc.team2557.robot.Robot;

public class Camera extends Subsystem {

    ITable contoursTable = NetworkTable.getTable("GRIP/goalContoursReport");

    private static final double fov = 45;
    public static final double cameraWidth = 158;
    private static final double targetWidth = 0.508;

    public static final double degreesPerWidth = fov / cameraWidth;

    @Override
    protected void initDefaultCommand() {

    }

    private double[] getValueArray(String name) {
        double[] values = contoursTable.getNumberArray(name, new double[0]);
        return values;
    }
    public Target[] getTargets() {
        double[] centerXs = this.getValueArray("centerX");
        double[] centerYs = this.getValueArray("centerY");
        double[] widths = this.getValueArray("width");
        double[] heights = this.getValueArray("height");
        double[] areas = this.getValueArray("area");
        double[] soliditys = this.getValueArray("solidity");

        Target[] targets = new Target[centerXs.length];

        for(int i = 0; i < targets.length; i++) {
            if(centerXs.length <= i
                    || centerYs.length <= i
                    || widths.length <= i
                    || heights.length <= i
                    || areas.length <= i
                    || soliditys.length <= i) {
                // All the data hasn't been collected yet
                continue;
            }

            targets[i] = new Target();

            targets[i].centerX = centerXs[i];
            targets[i].centerY = centerYs[i];

            targets[i].width = widths[i];
            targets[i].height = heights[i];

            targets[i].area = areas[i];
            targets[i].solidity = soliditys[i];

            targets[i].aspectRatio = targets[i].width / targets[i].height;
            targets[i].offset = targets[i].centerX - (targets[i].width / 2);
            // Distance calculation
            // Width of the target is proportional to its height.
            // We cannot depend on width being accurate, since different
            // perspectives give different widths,
            // however the height remains the same.
            // Using this reasoning, we can approximate the distance
            // to the target withing about a foot. If this error
            // is too large, we can account it with additional calculations.
            /*
             * width     widthp
             * -----  =  -----
             * height    heightp
             */
            double targetPixels = 0.508 * targets[i].height / 0.3048;
            targets[i].distance = (targetWidth * cameraWidth) / (2 * targetPixels * Math.tan(Math.toRadians(fov / 2)));
            // TODO: Angle from the target (0 is dead straight), assumes target is in center of camera

        }

        return targets;
    }

    public Camera.Target getTarget() {
        Camera.Target[] targets = Robot.camera.getTargets();
        Camera.Target trackedTarget = null;

        for(Target target : targets) {
            if(trackedTarget == null) {
                trackedTarget = target;
                continue;
            }

            if(target.width > trackedTarget.width) {
                trackedTarget = target;
                continue;
            }
        }

        return trackedTarget;
    }

    public class Target {
        public double centerX;
        public double centerY;

        public double width;
        public double height;

        public double area;
        public double solidity;

        public double aspectRatio;
        public double offset;

        /**
         * The distance of the target in meters
         */
        public double distance;
        /**
         * The angle the robot is from the center
         * line of the target in degrees
         */
        public double angleFromTarget;
    }

}
