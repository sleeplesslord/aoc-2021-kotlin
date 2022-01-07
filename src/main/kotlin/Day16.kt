import java.io.File

open class Packet(val version: Int, val typeId: Int) {
    var subPackets: MutableList<Packet> = mutableListOf()
    var value: Long = 0

    constructor(version: Int, typeId: Int, value: Long) : this(version, typeId) {
        this.value = value
    }

    fun versionSum(): Int {
        return version + subPackets.sumOf { it.versionSum() }
    }
}

fun parsePacket(bitString: String): Pair<Packet, String> {
    val version = bitString.substring(0, 3).toInt(2)
    val typeId = bitString.substring(3, 6).toInt(2)

    val functionMap = mapOf(
        0 to { input: Packet -> input.subPackets.sumOf { it.value } },
        1 to { input: Packet -> input.subPackets.fold(1L) { a, b -> a * b.value } },
        2 to { input: Packet -> input.subPackets.minOf { it.value }},
        3 to { input: Packet -> input.subPackets.maxOf { it.value }},
        4 to { input: Packet -> input.value },
        5 to { input: Packet -> if (input.subPackets[0].value > input.subPackets[1].value) 1L else 0L },
        6 to { input: Packet -> if (input.subPackets[0].value < input.subPackets[1].value) 1L else 0L },
        7 to { input: Packet -> if (input.subPackets[0].value == input.subPackets[1].value) 1L else 0L },
    )

    return if (typeId == 4) {
        parseLiteral(bitString.substring(6), version, typeId)
    } else {
        val guy = parseOperator(bitString.substring(6), version, typeId)
        guy.first.value = functionMap[typeId]!!.invoke(guy.first)
        return guy
    }
}

fun parseOperator(bitString: String, version: Int, typeId: Int): Pair<Packet, String> {
    val lengthTypeId = bitString.substring(0, 1).toInt()
    val operator = Packet(version, typeId)
    if (lengthTypeId == 0) {
        val totalLength = bitString.substring(1, 16).toInt(2)
        var subPacketString = bitString.substring(16, 16 + totalLength)
        while (subPacketString.isNotEmpty()) {
            val subPacket = parsePacket(subPacketString)
            subPacketString = subPacket.second
            operator.subPackets.add(subPacket.first)
        }

        return Pair(operator, bitString.substring(16 + totalLength))
    } else {
        val numPackets = bitString.substring(1, 12).toInt(2)
        var parsableBitString = bitString.substring(12)
        for (packet in 1..numPackets) {
            val subPacket = parsePacket(parsableBitString)
            parsableBitString = subPacket.second
            operator.subPackets.add(subPacket.first)
        }
        return Pair(operator, parsableBitString)
    }
}

fun parseLiteral(bitString: String, version: Int, typeId: Int): Pair<Packet, String> {
    var packetCount = 0
    var valueString = ""
    for (group in bitString.chunked(5)) {
        valueString += group.substring(1)
        packetCount++
        if (group[0] == '0') {
            break
        }
    }

    val value = valueString.toLong(2)
    return Pair(Packet(version, typeId, value), bitString.substring(valueString.length + packetCount))
}

fun parseHex(input: String): String {
    val hexMapping = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111",
    )
    return input.map { hexMapping[it]!! }.joinToString("")
}

fun day16(inputFile: String) {
    val input = File(inputFile).readLines().first()

    println(parsePacket(parseHex(input)).first.value)
}