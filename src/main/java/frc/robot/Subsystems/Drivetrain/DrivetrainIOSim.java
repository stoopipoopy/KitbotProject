// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Drivetrain;

import com.ctre.phoenixpro.controls.VoltageOut;
import com.ctre.phoenixpro.hardware.TalonFX;

import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import frc.robot.Subsystems.Constants;

/** Add your docs here. */
public class DrivetrainIOSim implements DrivetrainIO {

    TalonFX leftFalcon = new TalonFX(Constants.drivetrainLeftFalconID);
    TalonFX rightFalcon = new TalonFX(Constants.drivetrainRightFalconID);

    VoltageOut leftVoltage = new VoltageOut(0);
    VoltageOut rightVoltage = new VoltageOut(0);


    DifferentialDrivetrainSim physicsSim = DifferentialDrivetrainSim.createKitbotSim(
            KitbotMotor.kDoubleFalcon500PerSide, 
            KitbotGearing.k8p45, 
            KitbotWheelSize.kSixInch, 
    null);

    @Override
    public void updateInputs(DrivetrainIOInputs inputs) {
        physicsSim.update(0.020);


        var leftSimState = leftFalcon.getSimState();
        var rightSimState = rightFalcon.getSimState();

        inputs.leftOutputVolts = leftSimState.getMotorVoltage();
        inputs.rightOutputVolts = rightSimState.getMotorVoltage();

        inputs.leftOutputVolts = 0.0;
        inputs.rightOutputVolts = 0.0;

        inputs.leftVelocityMetersPerSecond = 0.0;
        inputs.rightVelocityMetersPerSecond = 0.0;

        inputs.leftPositionMeters = 0.0;
        inputs.rightPositionMeters = 0.0;

        inputs.leftCurrentAmps = new double[0];
        inputs.leftTempCelsius = new double[0];
        inputs.rightCurrentAmps = new double[0];
        inputs.rightTempCelsius = new double[0];
    }

    @Override
    public void setVolts(double left, double right) {
        // TODO Auto-generated method stub
        leftFalcon.setControl(leftVoltage.withOutput(left));
        rightFalcon.setControl(rightVoltage.withOutput(left));

        
    }}
