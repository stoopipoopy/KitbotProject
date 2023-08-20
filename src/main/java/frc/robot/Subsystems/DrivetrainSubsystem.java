// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenixpro.controls.VoltageOut;
import com.ctre.phoenixpro.hardware.TalonFX;


/** Add your docs here. */
public class DrivetrainSubsystem {
    TalonFX leftFalcon = new TalonFX(Constants.drivetrainLeftFalconID);
    TalonFX rightFalcon = new TalonFX(Constants.drivetrainRightFalconID);

    VoltageOut leftVoltage = new VoltageOut(0);
    VoltageOut rightVoltage = new VoltageOut(0);   

    public DrivetrainSubsystem() {

    }
    private void setVoltages(double left, double right) {
        leftFalcon.setControl(leftVoltage.withOutput(left));
        rightFalcon.setControl(rightVoltage.withOutput(left));
    }

}

