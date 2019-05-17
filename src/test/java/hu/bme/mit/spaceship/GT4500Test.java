package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore MockPrimary;
  private TorpedoStore MockSecondary;

  @BeforeEach
  public void init(){
    MockPrimary = mock(TorpedoStore.class);
    MockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(MockPrimary, MockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(0)).fire(1);

  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Cooling(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true, result2);
    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_First_Empty(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(true);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(MockPrimary, times(0)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_First_EmptyAfterShot(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(true);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    when(MockPrimary.isEmpty()).thenReturn(true);

    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(false, result2);

    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Secondary_Empty(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(true);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(true, result2);

    verify(MockPrimary, times(2)).fire(1);
    verify(MockSecondary, times(0)).fire(1);
  }
  @Test
  public void fireTorpedo_Both_Empty_Fire_ALL(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(true);
    when(MockSecondary.isEmpty()).thenReturn(true);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);

    verify(MockPrimary, times(0)).fire(1);
    verify(MockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Both_Empty_Fire_SINGLES(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(true);
    when(MockSecondary.isEmpty()).thenReturn(true);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    assertEquals(false, result2);

    verify(MockPrimary, times(0)).fire(1);
    verify(MockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_First_Failure(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(false);
    when(MockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);

    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Secondary_Failure(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(true);
    when(MockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    assertEquals(false, result2);


    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Both_Failure_FIRE_ALL(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(false);
    when(MockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);


    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Both_Failure_FIRE_SINGLES(){
    // Arrange
    when(MockPrimary.isEmpty()).thenReturn(false);
    when(MockSecondary.isEmpty()).thenReturn(false);
    when(MockPrimary.fire(1)).thenReturn(false);
    when(MockSecondary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    assertEquals(false, result2);


    verify(MockPrimary, times(1)).fire(1);
    verify(MockSecondary, times(1)).fire(1);
  }

}
