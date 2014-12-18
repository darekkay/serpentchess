/**
 * Copyright (C) 2013 Dariusz Krolikowski, Anna Geringer, Jens Meinicke
 *
 * This file is part of SerpentChess.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ovgu.serpentchess.view.chessboard;

public class PointCalculatorTest {

	// TODO Test does not work anymore because of offset @ calculatePointMap()
	// @Test
	// public void testCalculatePointList(){
	// PointCalculator calc = new PointCalculator();
	//
	// BiMap<Point, Field> testMap = HashBiMap.create();
	//
	// int i = 1;
	// for(int y = 600; y < 2001; y = y + 200){
	// testMap.put(new Point(50,y), new Field(1,i));
	// i++;
	// }
	//
	// i = 1;
	// for(int y = 500; y < 2101; y = y + 200){
	// testMap.put(new Point(150,y), new Field(2,i));
	// i++;
	// }
	//
	// i = 1;
	// for(int y = 400; y < 2201; y = y + 200){
	// testMap.put(new Point(250,y), new Field(3,i));
	// i++;
	// }
	//
	//
	// i = 1;
	// for(int y = 300; y < 2301; y = y + 200){
	// testMap.put(new Point(350,y), new Field(4,i));
	// i++;
	// }
	//
	// i = 1;
	// for(int y = 200; y < 2401; y = y + 200){
	// testMap.put(new Point(450,y), new Field(5,i));
	// i++;
	// }
	//
	// i = 1;
	// for(int y = 100; y < 2501; y = y + 200){
	// testMap.put(new Point(550,y), new Field(6,i));
	// i++;
	// }
	//
	//
	// i = 2;
	// for(int y = 200; y < 2401; y = y + 200){
	// testMap.put(new Point(650,y), new Field(7,i));
	// i++;
	// }
	//
	// i = 3;
	// for(int y = 300; y < 2301; y = y + 200){
	// testMap.put(new Point(750,y), new Field(8,i));
	// i++;
	// }
	//
	// i = 4;
	// for(int y = 400; y < 2201; y = y + 200){
	// testMap.put(new Point(850,y), new Field(9,i));
	// i++;
	// }
	//
	// i = 5;
	// for(int y = 500; y < 2101; y = y + 200){
	// testMap.put(new Point(950,y), new Field(10,i));
	// i++;
	// }
	//
	// i = 6;
	// for(int y = 600; y < 2001; y = y + 200){
	// testMap.put(new Point(1050,y), new Field(11,i));
	// i++;
	// }
	//
	// i = 7;
	// for(int y = 700; y < 1901; y = y + 200){
	// testMap.put(new Point(1150,y), new Field(12,i));
	// i++;
	// }
	//
	// i = 8;
	// for(int y = 800; y < 1801; y = y + 200){
	// testMap.put(new Point(1250,y), new Field(13,i));
	// i++;
	// }
	//
	// BiMap<Point, Field> calculatedMap = calc.calculatePointMap(2600, 1300);
	//
	// assertEquals(testMap, calculatedMap);
	// }
}
