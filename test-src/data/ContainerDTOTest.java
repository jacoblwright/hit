package data;

import static org.junit.Assert.*;
import gui.common.SizeUnits;

import org.junit.Test;

public class ContainerDTOTest {

	@Test
	public void containerDTOTest() {
		ContainerDTO c = new ContainerDTO();
		Enum<SizeUnits> unit = SizeUnits.Unspecified;
		System.out.println(unit.name());
		c.setUnit(unit.name());
		assertEquals(unit, c.getUnit());
	}
}
