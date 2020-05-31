var ASM = Java.type('net.minecraftforge.coremod.api.ASMAPI');
var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

function initializeCoreMod() {
    return {
        'calculate-celestial-angle': {
            'target': {
                'type': 'CLASS',
                'name': 'net/minecraft/world/IWorld'
            },
            'transformer': function (cn) {
                cn.methods.forEach(function (mn) {
                    if (mn.name === ASM.mapMethod('func_72826_c')) {
                        for (var iterator = mn.instructions.iterator(); iterator.hasNext();) {
                            var node = iterator.next();
                            if (node.getOpcode() === Opcodes.INVOKEVIRTUAL && node.name === ASM.mapMethod("func_76563_a")) {
                                mn.instructions.insertBefore(node, new VarInsnNode(Opcodes.ALOAD, 0));
                                iterator.set(new MethodInsnNode(Opcodes.INVOKESTATIC, "roito/afterthedrizzle/common/handler/AsmHandler", "getSeasonCelestialAngle", "(JFLnet/minecraft/world/IWorld;)F", false));
                            }
                        }
                    }
                 });
            return cn;
        }
    }
}
}