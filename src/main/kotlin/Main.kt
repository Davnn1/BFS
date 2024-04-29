import java.lang.Exception

fun <T> ArrayList<T>.enqueue(value: T) {
	this.add(value)
}

fun <T> ArrayList<T>.dequeue(): T {
	val value = this[0]
	this.removeAt(0)
	return value
}

//A--B--E--H
// \  \  \ |
//  C- G  O|
//   \\    |
//    F ----
//     \
//      G
val map = ArrayList<Table>()

fun main(args: Array<String>) {
	map.add(Table("A", "col1", "B", "col2"))
	map.add(Table("C", "col1", "A", "col2"))
	map.add(Table("B", "col1", "E", "col2"))
//	map.add(Table("B", "col1", "G", "col2"))
	map.add(Table("C", "col1", "F", "col2"))
	map.add(Table("F", "col1", "G", "col2"))
	map.add(Table("E", "col1", "H", "col2"))
	map.add(Table("E", "col1", "O", "col2"))

	map.add(Table("C", "col1", "H", "col2"))
//	map.add(Table("H","","C",""))

//	//MAP 2
//	map.add(Table("A", "col1", "C", "col2"))
//	map.add(Table("C", "col1", "D", "col2"))
//	map.add(Table("D", "col1", "B", "col2"))
//	map.add(Table("B", "col1", "E", "col2"))
	

	val start = "A"
	val end = listOf("H", "G")
	
	val paths = mutableSetOf<String>()
	
	end.forEach {
		val path = findPath(start, it)
		paths.addAll(path)
	}
	
}

fun findPath(start: String, end: String): MutableList<String> {
	// TODO list<List<string>
	var counter = 0
	var isFound = false
	var endNode: Node? = null

	val tree = Node()
	tree.value = start


	val queue = ArrayList<Node>()
	queue.add(tree)

	while (counter < 10 && !isFound) {
		val tempQueue = ArrayList<Node>()

		while (queue.isNotEmpty() && !isFound) {
			var currentNode = queue.dequeue()

			val filteredMap = map.filter {
				// filter tabel start bisa kemana aja
				it.tableName1 == currentNode.value
			}.toMutableList()

			map.filter {
				// filter tabel start bisa kemana aja
				it.tableName2 == currentNode.value
			}.forEach {
				filteredMap.add(Table(it.tableName2, it.columnName2, it.tableName1, it.columnName1))
			}

			for (table in filteredMap) {
				if (table.tableName2 == end) {
					isFound = true
					endNode = Node(end, currentNode)
					break;
				}
				tempQueue.add(Node(value = table.tableName2, parent = currentNode))
				currentNode.child.add(Node(value = table.tableName2, parent = currentNode))
			}
		}

		queue.addAll(tempQueue)
		counter++
	}

	if (!isFound)
		println("found $end, from $start, not found")

	println("found $end, from $start, with $counter step(s)")

	val result = mutableListOf<String>()

	print("with path:")
	while (endNode?.parent != null) {
		print(endNode.value)
		
		
		try {
			map.filter {
				it.tableName1 == endNode?.value && it.tableName2 == endNode?.parent?.value
			}[0].let {
//              result.add("${it.tableName1}.${it.columnName1}|${it.tableName2}.${it.columnName2}")
				result.addAll(
					arrayOf(it.tableName1, it.tableName2)
				)
			}
		} catch (e: Exception) {
			map.filter {
				it.tableName2 == endNode?.value && it.tableName1 == endNode?.parent?.value
			}[0].let {
//              result.add("${it.tableName1}.${it.columnName1}|${it.tableName2}.${it.columnName2}")
				result.addAll(
					arrayOf(it.tableName1, it.tableName2)
				)
			}
		}



		endNode = endNode.parent
	}
	print(endNode?.value)
	return result
}

fun main1() {
	val temp = mutableSetOf<Table>()
	temp.add(Table("A", "col1", "B", "col2"))
	temp.add(Table("C", "col1", "A", "col2"))
	
	
}