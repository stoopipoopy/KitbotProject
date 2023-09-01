// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Drivetrain;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenixpro.controls.VoltageOut;
import com.ctre.phoenixpro.hardware.TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Constants;


/** Add your docs here. */
public class DrivetrainSubsystem extends SubsystemBase {

    DrivetrainIO io = new DrivetrainIOSim();
    DrivetrainIOInputsAutoLogged inputs = new DrivetrainIOInputsAutoLogged();

    DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0);

    

    TalonFX leftFalcon = new TalonFX(Constants.drivetrainLeftFalconID);
    TalonFX rightFalcon = new TalonFX(Constants.drivetrainRightFalconID);

    Field2d field = new Field2d();

    VoltageOut leftVoltage = new VoltageOut(0);
    VoltageOut rightVoltage = new VoltageOut(0);   
    

    @Override
    public void periodic() {
        field.setRobotPose(odometry.getPoseMeters());
        SmartDashboard.putData(field);
        
        io.updateInputs(inputs);
        Logger.getInstance().processInputs("Drivetrain", inputs);
        odometry.update(
            odometry.getPoseMeters().getRotation()
                .plus(
                    Rotation2d.fromRadians(
                        (inputs.leftVelocityMetersPerSecond - inputs.rightVelocityMetersPerSecond)
                        * 0.020 / Units.inchesToMeters(26))), inputs.leftPositionMeters, inputs.rightPositionMeters);

        Logger.getInstance().recordOutput("Drivebase Pose", odometry.getPoseMeters());
    }

    public DrivetrainSubsystem() {

    }
    private void setVoltages(double left, double right) {
        io.setVolts(left, right);
    }
    public CommandBase setVoltagesArcadeCommand(DoubleSupplier drive, DoubleSupplier steer) {
        return new RunCommand(() -> {
            var speeds = DifferentialDrive.arcadeDriveIK(drive.getAsDouble(), steer.getAsDouble(), false);
            this.setVoltages(speeds.left * 12, speeds.right * 12);
        }, this);
    }
    
}

